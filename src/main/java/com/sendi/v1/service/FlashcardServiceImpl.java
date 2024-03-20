package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.domain.Image;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.repo.ImageRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.FlashcardDTORepresentable;
import com.sendi.v1.service.model.FlashcardImageDTO;
import com.sendi.v1.service.model.mapper.DeckMapper;
import com.sendi.v1.service.model.mapper.FlashcardMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlashcardServiceImpl implements FlashcardService {
    private final FlashcardRepository flashcardRepo;
    private final DeckMapper deckMapper;
    private final FlashcardMapper flashcardMapper;
    private final DeckRepository deckRepo;
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    @Override
    public FlashcardDTORepresentable getOneById(Long flashcardId) {
        Optional<Flashcard> flashcardOptional = flashcardRepo.findById(flashcardId);

        if (flashcardOptional.isEmpty()) {
            throw new RuntimeException("Invalid flashcardId");
        }

        Flashcard flashcard = flashcardOptional.get();
        FlashcardDTO newFlashcardDTO = flashcardMapper.toDTO(flashcard);

        if (!Objects.isNull(flashcard.getImage())) {
            return FlashcardImageDTO.builder()
                    .bytes(flashcard.getImage().getData())
                    .flashcardDTO(newFlashcardDTO)
                    .build();
        }

        return newFlashcardDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deckDTO) {
        if (deckDTO == null) {
            return Collections.emptyList();
        }

        Deck deck = deckMapper.toEntity(deckDTO);

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck)
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        log.info("flashcardDTOList: {}", flashcardDTOList);

        return flashcardDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId) {
        return getFlashcardsByDeckId(deckId, Pageable.unpaged());
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable) {
        List<FlashcardImageDTO> flashcardDTOList = flashcardRepo.findAllByDeckId(deckId)
                .stream()
                .map(flashcard -> {
                    if (!Objects.isNull(flashcard.getImage()) && !Objects.isNull(flashcard.getImage().getData())) {
                        return FlashcardImageDTO.builder()
                                .bytes(flashcard.getImage().getData())
                                .flashcardDTO(flashcardMapper.toDTO(flashcard))
                                .build();
                    }

                    return FlashcardImageDTO.builder()
                            .bytes(null)
                            .flashcardDTO(flashcardMapper.toDTO(flashcard))
                            .build();
                })
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, int page, int size) {
        return getFlashcardsByDeckId(deckId, PageRequest.of(page, size));
    }

    //Send the filenames as the orderIds, so that it will be matched with the flashcards
    @Override
    public Object createOrUpdate(Long deckId, List<FlashcardDTO> flashcardDTOList, HttpServletRequest httpServletRequest) throws IOException {
        System.out.println(httpServletRequest instanceof MultipartHttpServletRequest);
        if (httpServletRequest instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> multipartFileMap = ((MultipartHttpServletRequest) httpServletRequest).getFileMap();
            log.info("multipartfile: {}", multipartFileMap);
            List<FlashcardDTO> flashcardDTOsWithoutFiles = new ArrayList<>();
            List<FlashcardDTO> flashcardDTOsWithFiles = new ArrayList<>();
            flashcardDTOList.forEach(flashcardDTO -> {
                String orderId = String.valueOf(flashcardDTO.getOrderId());
                MultipartFile multipartFile = multipartFileMap.get(orderId);
                if (multipartFile == null) {
                    System.out.println("null multipartfile");
                    flashcardDTOsWithoutFiles.add(flashcardDTO);
                } else {
                    flashcardDTOsWithFiles.add(flashcardDTO);
                }
            });

            List<FlashcardImageDTO> resultList = new ArrayList<>();

            if (!flashcardDTOsWithoutFiles.isEmpty()) {
                resultList = createOrUpdate(deckId, flashcardDTOsWithoutFiles)
                        .stream()
                        .map(flashcardDTO -> new FlashcardImageDTO(flashcardDTO, null))
                        .collect(Collectors.toList());
            }

            Map<FlashcardDTO, MultipartFile> flashcardDTOMultipartFileMap =
                    flashcardDTOsWithFiles.stream().collect(
                            Collectors.toMap(Function.identity(),
                                    flashcardDTO -> {
                                        String orderId = String.valueOf(flashcardDTO.getOrderId());
                                        return multipartFileMap.get(orderId);
                                    }
                            ));
            log.info("flashcardDTOMultipartFileMap => {}", flashcardDTOMultipartFileMap);

            resultList.addAll(createOrUpdate(deckId, flashcardDTOMultipartFileMap));
            return resultList;
        }

        return createOrUpdate(deckId, flashcardDTOList);
    }

    private List<FlashcardImageDTO> createOrUpdate(Long deckId,
                                                   Map<FlashcardDTO, MultipartFile> flashcardDTOMultipartFileMap
    ) throws IOException {
        Deck deck = Optional.of(deckRepo.findById(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId))
                .get();
        log.info("flashcardDTOMultipartFileMap: {}", flashcardDTOMultipartFileMap);

        List<Image> images = flashcardDTOMultipartFileMap.entrySet().stream()
                .map(entry -> createImageFromFlashcardDTO(entry.getValue(), entry.getKey(), deck))
                .collect(Collectors.toList());

        List<Image> savedImages = imageRepository.saveAll(images);
        return savedImages.stream()
                .map(image -> new FlashcardImageDTO(flashcardMapper.toDTO(image.getFlashcard()), image.getData()))
                .collect(Collectors.toList());
    }


    private Image createImageFromFlashcardDTO(MultipartFile multipartFile, FlashcardDTO flashcardDTO, Deck deck) {
        {
            try {
                Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
                flashcard.setDeck(deck);

                Image image = Image.builder()
                        .data(multipartFile.getBytes())
                        .size(multipartFile.getSize())
                        .name(multipartFile.getOriginalFilename())
                        .flashcard(flashcard)
                        .contentType(multipartFile.getContentType())
                        .build();
                return image;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<FlashcardDTO> createOrUpdate(Long deckId, List<FlashcardDTO> flashcardDTOs) throws IOException {
        log.info("in service");
        Deck deck = Optional.of(deckRepo.findById(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId))
                .get();

        List<Flashcard> flashcards = flashcardDTOs.stream()
                .map(flashcardDTO -> {
                    try {
                        Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
                        flashcard.setDeck(deck);
                        return flashcard;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        flashcardRepo.saveAll(flashcards);

        List<FlashcardDTO> flashcardDTOList = flashcards
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Override
    public List<FlashcardDTO> createOrUpdate(Long deckId, MultipartFile multipartFile) throws IOException {
        File excFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(excFile);
        FileInputStream fileInputStream = new FileInputStream(excFile);
        Workbook workbook = null;

        List<FlashcardDTO> flashcardDTOList = new ArrayList<>();

        if (excFile.getName().toLowerCase().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } else if (excFile.getName().toLowerCase().endsWith("xls")) {
            workbook = new HSSFWorkbook(fileInputStream);
        }

        int numberOfSheets = Objects.requireNonNull(workbook).getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);

            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                String term = "";
                String definition = "";

                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (Objects.requireNonNull(cell.getCellType()) == CellType.STRING) {
                        if (term.equalsIgnoreCase("")) {
                            term = cell.getStringCellValue().trim();
                        } else if (definition.equalsIgnoreCase("")) {
                            definition = cell.getStringCellValue().trim();
                        }
                    }
                }
                FlashcardDTO flashcardDTO = FlashcardDTO.builder()
                        .term(term)
                        .definition(definition)
                        .build();

                flashcardDTOList.add(flashcardDTO);
            }

            fileInputStream.close();
            excFile.delete();
        }

        return createOrUpdate(deckId, flashcardDTOList);
    }

    @Transactional
    @Override
    public void deleteById(Long flashcardId) {
        flashcardRepo.deleteById(flashcardId);
    }
}
