package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionMaker {
    private FlashcardRepository flashcardRepository;

//    public Set<Question> make(long deckId, int questionCount, AnswerWith answerWith) {
//        List<Flashcard> flashcards = getFlashcardsAndShuffleByQuestionCount(deckId, questionCount);
////        return convertToQuestions(flashcards, answerWith);
//    }

    private List<Flashcard> getFlashcards(long deckId) {
        return flashcardRepository.findAllByDeckId(deckId);
    }

    private List<Flashcard> getFlashcardsAndShuffleByQuestionCount(long deckId, int questionCount) {
        List<Flashcard> flashcards = getFlashcards(deckId);
        Collections.shuffle(flashcards);
        return flashcards.subList(0, questionCount);
    }

//    private Set<Question> convertToQuestions(List<Flashcard> flashcards, AnswerWith answerWith) {
//        Set<Question> questions = new HashSet<>();
//        if (answerWith.equals(AnswerWith.TERM)) {
//            for (Flashcard flashcard : flashcards) {
//                Question question = new Question();
//                question.setQuestion(flashcard.getDefinition());
//                question.setAnswer(flashcard.getTerm());
//                questions.add(question);
//            }
//        } else if (answerWith.equals(AnswerWith.DEFINITION)) {
//            for (Flashcard flashcard : flashcards) {
//                Question question = new Question();
//                question.setQuestion(flashcard.getTerm());
//                question.setAnswer(flashcard.getDefinition());
//                questions.add(question);
//            }
//        } else {
//            for (int i = 0; i < flashcards.size(); i++) {
//                Question question = new Question();
//                if (i % 2 == 0) {
//                    question.setQuestion(flashcards.get(i).getDefinition());
//                    question.setAnswer(flashcards.get(i).getTerm());
//                } else {
//                    question.setQuestion(flashcards.get(i).getTerm());
//                    question.setAnswer(flashcards.get(i).getDefinition());
//                }
//            }
//        }
//        return questions;
//    }
}
