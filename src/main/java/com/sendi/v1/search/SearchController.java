package com.sendi.v1.search;

import com.sendi.v1.security.config.permission.DeckReadPermission;
import com.sendi.v1.security.config.permission.FlashcardReadPermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;

    @DeckReadPermission
    @FlashcardReadPermission
    @GetMapping(params = "query")
    public ResponseEntity<SearchResponse> search(@RequestParam("query") String query) {
        log.info("Request to search coming in");
        return ResponseEntity.ok(searchService.search(query));
    }
}
