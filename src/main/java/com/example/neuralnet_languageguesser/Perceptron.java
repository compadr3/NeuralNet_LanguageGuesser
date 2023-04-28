package com.example.neuralnet_languageguesser;

import java.util.ArrayList;

public class Perceptron {
    ArrayList<Double> wektorWag;
    String trainedLanguage;
    double prOg;
    double parametrAlfa;

    //dla kazdego iksa(z wektora input): suma += wektorWag*xWektoraInput
    Perceptron(int indexOfClassInVector, double parametrAlfa, double prOg, String trainedLanguage) {
        this.parametrAlfa = parametrAlfa;
        this.wektorWag = new ArrayList<>();
        this.prOg = prOg;
        this.trainedLanguage = trainedLanguage;
        for (int i = 0; i < indexOfClassInVector; i++) {
            wektorWag.add(Math.random() * 1);
        }
    }

    public String getTrainedLanguage() {
        return trainedLanguage;
    }

    public void train(String[] line, int correctAnswer) {

        double sumaE = 0;
        int y;
        for (int i = 0; i < line.length - 1; i++) {
            sumaE += Double.parseDouble(line[i]) * this.wektorWag.get(i);
        }
        //System.out.println(sumaE);
        if (sumaE > prOg) {
            y = 1;
        } else {
            y = 0;
        }
        if (y != correctAnswer) {
            ArrayList<Double> wektorWagPrim = new ArrayList<>(this.wektorWag);
            for (int i = 0; i < line.length - 1; i++) {
                wektorWagPrim.set(i, (this.wektorWag.get(i) + ((correctAnswer - y) * parametrAlfa * Double.parseDouble(line[i]))));
            }
            this.wektorWag = wektorWagPrim;
            this.prOg = prOg + (correctAnswer - y) * parametrAlfa * -1;
        }
    }

    public int check(String[] line) {
        double sumaE = 0;
        for (int i = 0; i < line.length - 1; i++) {
            sumaE += Double.parseDouble(line[i]) * wektorWag.get(i);
        }
        if (sumaE > prOg) {
            return 1;
        } else {
            return 0;
        }
    }

    public double calculate(String[] line) {
        double sumaE = 0;
        for (int i = 0; i < line.length - 1; i++) {
            sumaE += Double.parseDouble(line[i]) * wektorWag.get(i);
        }

        return sumaE - prOg;
    }

}


