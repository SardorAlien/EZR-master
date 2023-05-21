package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseMaker {
    private QuestionMaker questionMaker;
    private TestResponse testResponse;
    private FlashcardRepository flashcardRepository;

    public TestResponse make(long deckId,
                             int questionCount,
                             boolean isTrueFalseQuestionsIncluded,
                             boolean isMultipleChoiceIncluded,
                             boolean isMatchingQuestionsIncluded,
                             boolean isWrittenIncluded,
                             AnswerWith answerWith) {
        int countQuestionTypes = 0;
        if (isTrueFalseQuestionsIncluded) countQuestionTypes++;
        if (isMultipleChoiceIncluded) countQuestionTypes++;
        if (isMatchingQuestionsIncluded) countQuestionTypes++;
        if (isWrittenIncluded) countQuestionTypes++;

        List<Flashcard> flashcardsWillBeEliminated = getFlashcards(deckId);
        List<Flashcard> allFlashcards = List.copyOf(flashcardsWillBeEliminated);

        int randomFlashcardId = 0;

        if (isTrueFalseQuestionsIncluded) {
            for (int i = 0; i < countQuestionTypes; i++) {
                randomFlashcardId = new Random().ints(0, flashcardsWillBeEliminated.size()).findFirst().getAsInt();
                Flashcard flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);

                TrueFalseQuestion trueFalseQuestion = TrueFalseQuestion.builder()
                        .question(flashcard.getTerm())
                        .answer(flashcard.getDefinition())
                        .flashcardId(flashcard.getId())
                        .build();

                testResponse.addTrueFalseQuestion(trueFalseQuestion);

                flashcardsWillBeEliminated.remove(randomFlashcardId);
            }
        }

        if (isMultipleChoiceIncluded) {
            for (int i = 0; i < countQuestionTypes; i++) {
                randomFlashcardId = new Random().ints(0, flashcardsWillBeEliminated.size()).findFirst().getAsInt();
                Flashcard flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);

                int randomFlashcardIdForSecondary1 = new Random()
                        .ints(0, flashcardsWillBeEliminated.size())
                        .findFirst()
                        .getAsInt();

                int randomFlashcardIdForSecondary2 = new Random()
                        .ints(0, flashcardsWillBeEliminated.size())
                        .findFirst()
                        .getAsInt();

                int randomFlashcardIdForSecondary3 = new Random()
                        .ints(0, flashcardsWillBeEliminated.size())
                        .findFirst()
                        .getAsInt();

                MultipleChoiceQuestion multipleChoiceQuestion = MultipleChoiceQuestion.builder()
                        .question(flashcard.getTerm())
                        .actualAnswer(flashcard.getDefinition())
                        .secondaryAnswer1(flashcardsWillBeEliminated.get(randomFlashcardIdForSecondary1).getDefinition())
                        .secondaryAnswer2(flashcardsWillBeEliminated.get(randomFlashcardIdForSecondary2).getDefinition())
                        .secondaryAnswer3(flashcardsWillBeEliminated.get(randomFlashcardIdForSecondary3).getDefinition())
                        .flashcardId(flashcard.getId())
                        .build();

                testResponse.addMultipleChoiceQuestion(multipleChoiceQuestion);

                flashcardsWillBeEliminated.remove(randomFlashcardId);
            }
        }

        if (isMatchingQuestionsIncluded) {
            for (int i = 0; i < countQuestionTypes; i++) {
                randomFlashcardId = new Random().ints(0, flashcardsWillBeEliminated.size()).findFirst().getAsInt();
                Flashcard flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);

                MatchingQuestion matchingQuestion = MatchingQuestion.builder()
                        .question(flashcard.getTerm())
                        .answer(flashcard.getDefinition())
                        .flashcardId(flashcard.getId())
                        .build();

                testResponse.addMatchingQuestion(matchingQuestion);

                flashcardsWillBeEliminated.remove(randomFlashcardId);
            }
        }

        if (isWrittenIncluded) {
            for (int i = 0; i < countQuestionTypes; i++) {
                randomFlashcardId = new Random().ints(0, flashcardsWillBeEliminated.size()).findFirst().getAsInt();
                Flashcard flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);

                WrittenQuestion writtenQuestion = WrittenQuestion.builder()
                        .question(flashcard.getTerm())
                        .answer(flashcard.getDefinition())
                        .flashcardId(flashcard.getId())
                        .build();

                testResponse.addWrittenQuestion(writtenQuestion);

                flashcardsWillBeEliminated.remove(randomFlashcardId);
            }
        }

        return testResponse;
    }

    private List<Flashcard> getFlashcards(long deckId) {
        return flashcardRepository.findAllByDeckId(deckId);
    }

    private List<Flashcard> getFlashcardsAndShuffleByQuestionCount(long deckId, int questionCount) {
        List<Flashcard> flashcards = getFlashcards(deckId);
        Collections.shuffle(flashcards);
        return flashcards.subList(0, questionCount);
    }
}
