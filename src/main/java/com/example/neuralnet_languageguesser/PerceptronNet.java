package com.example.neuralnet_languageguesser;

import java.util.*;

public class PerceptronNet {

    ArrayList<Perceptron> perceptrons = new ArrayList<>();

    public PerceptronNet(Perceptron... perceptrons) {
        Collections.addAll(this.perceptrons, perceptrons);
    }

    public PerceptronNet(ArrayList<Perceptron> perceptrons) {
        this.perceptrons = perceptrons;
    }

    public String checkOneLanguage(String text) {
        String[] vector = readFromString(text);


        double maxScore = -1;
        String guessedLanguage = "";
        for (Perceptron perceptron : perceptrons) {
            if (perceptron.calculate(vector) > maxScore) {
                maxScore = perceptron.calculate(vector);
                guessedLanguage = perceptron.getTrainedLanguage();
            }
        }

        return guessedLanguage;

    }

    public void checkTestLanguages(ArrayList<String[]> vectors) {
        double maxScore = -1;
        String guessedLanguage = "";
        String currentLanguage = vectors.get(0)[vectors.get(0).length - 1];

        int correctGuessCounter = 0;
        int languageCounter = 0;
        int counter = 0;
        System.out.println("Test languages:");
        for (int i = 0; i < vectors.size(); i++) {
            String[] vector = vectors.get(i);
            languageCounter++;

            if (!currentLanguage.equals(vector[vector.length - 1])) {
                //System.out.println("Current: " + vector[vector.length - 1] + ", PC: " + guessedLanguage + ", current: " + currentLanguage);
                if (counter == 0) {
                    languageCounter -= 1;
                    counter = 1;
                }


                System.out.println("\n\n\nAccuracy for language " + currentLanguage + ": "
                        + ((double) (correctGuessCounter) / languageCounter) * 100 + "% - " + correctGuessCounter + "/" + languageCounter + "\n\n\n");

                currentLanguage = vector[vector.length - 1];
                maxScore = -1;
                languageCounter = 0;
                correctGuessCounter = 0;
            }

            System.out.println(Arrays.toString(vector));

            for (Perceptron perceptron : perceptrons) {
                if (perceptron.calculate(vector) > maxScore) {
                    maxScore = perceptron.calculate(vector);
                    guessedLanguage = perceptron.getTrainedLanguage();
                }
            }

            if (guessedLanguage.equals(currentLanguage)) {
                correctGuessCounter++;
            }

            System.out.println("Guessed by PC: " + guessedLanguage);


            if (i == vectors.size() - 1) {
                System.out.println("ALA ma kota");
                System.out.println("\n\n\nAccuracy for language " + currentLanguage + ": "
                        + ((double) (correctGuessCounter) / (languageCounter + 1)) * 100 + "% - " + correctGuessCounter + "/" + (languageCounter + 1) + "\n\n\n");
            }

        }
    }

    public String[] readFromString(String input) {
        double[] result = new double[27];

        Map<Character, Integer> charMap = new HashMap<>();
        for (int i = 97; i <= 122; i++) {
            charMap.put((char) i, 0);
        }

        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (charMap.containsKey(aChar)) {
                int value = charMap.get(aChar);
                charMap.put(aChar, value + 1);
            }
        }
        int counter = 0;
        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            result[counter] = entry.getValue();
            counter += 1;
        }
        return changeArrayDoubleToString(result);
    }

    public String[] changeArrayDoubleToString(double[] d) {
        String[] s = new String[d.length];

        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(d[i]);
        }
        s[26] = "LANG CLASS";
        return s;
    }

}

