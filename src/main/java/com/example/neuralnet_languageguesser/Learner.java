package com.example.neuralnet_languageguesser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Learner {
    public static PerceptronNet perceptronNet;
    public static ArrayList<String> directoryNames;
    public static void learn(){
        Futil.processDir("C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\Train",
                "C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\TrainOUT.txt", true);
        Futil.processDir("C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\Test",
                "C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\TestOUT.txt", false);

        ArrayList<String[]> trainList = Futil.trainList;
        ArrayList<String[]> testList = Futil.testList;
        directoryNames = Futil.directoryNames;

        int indexOfClass = trainList.get(0).length - 1;

        //wersja "automatyczna"
        System.out.println(Futil.mapOfLanguages);
        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        for (
                Map.Entry<String, Integer> entry : Futil.mapOfLanguages.entrySet()) {
            System.out.println(entry.getKey());
            perceptrons.add(new Perceptron(indexOfClass, 0.1, 0.55, entry.getKey()));
        }

        learnAll(2000, trainList, perceptrons);

        perceptronNet = new PerceptronNet(perceptrons);

        //

        System.out.println("Calculate: ");

        perceptronNet.checkTestLanguages(testList);
    }

    public static void learnAll(int howManyTimes, ArrayList<String[]> allVectors, ArrayList<Perceptron> perceptrons) {
        int indexOfClass = allVectors.get(0).length - 1;
        for (int k = 0; k < howManyTimes; k++) {
            for (Perceptron perceptron : perceptrons) {
                for (String[] vector : allVectors) {
                    String tmpKey = vector[indexOfClass];
                    perceptron.train(vector, tmpKey.equals(perceptron.getTrainedLanguage()) ? 1 : 0);
                }
            }
            Collections.shuffle(allVectors);
        }

    }
}
