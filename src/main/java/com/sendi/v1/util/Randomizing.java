package com.sendi.v1.util;

import com.sendi.v1.domain.Flashcard;

import java.util.List;
import java.util.Random;

public class Randomizing {
    public static int getRandomNumber(int boundary) {
        return new Random()
                .ints(0, boundary)
                .findFirst()
                .getAsInt();
    }

    public static int[] getRandomFlashcardIdsForSecondaryAnswers(int flashcardsSize) {
        int[] randomFlashcardIds = new int[3];
        randomFlashcardIds[0] = Randomizing.getRandomNumber(flashcardsSize);
        while (randomFlashcardIds[1] == randomFlashcardIds[0]) {
            randomFlashcardIds[1] = Randomizing.getRandomNumber(flashcardsSize);
        }

        while (randomFlashcardIds[2] == randomFlashcardIds[0] ||
                randomFlashcardIds[2] == randomFlashcardIds[1]) {
            randomFlashcardIds[2] = Randomizing.getRandomNumber(flashcardsSize);
        }

        return randomFlashcardIds;
    }
}
