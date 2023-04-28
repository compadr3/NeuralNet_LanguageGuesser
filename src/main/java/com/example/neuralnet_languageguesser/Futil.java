package com.example.neuralnet_languageguesser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

public class Futil {

    static Pattern languageShortNameInDirectory;
    static ArrayList<String[]> trainList = new ArrayList<>();
    static ArrayList<String[]> testList = new ArrayList<>();

    public static ArrayList<String> directoryNames = new ArrayList();
    static HashMap<String, Integer> mapOfLanguages = new HashMap<>();

    public static void processDir(String dirName, String resultFileName, boolean training) {
        if (training) {
            languageShortNameInDirectory = Pattern.compile("Train\\\\(.+?)\\\\");
        } else {
            languageShortNameInDirectory = Pattern.compile("Test\\\\(.+?)\\\\");
        }
        try {

            Writer fileWriter = new OutputStreamWriter(new FileOutputStream(resultFileName), StandardCharsets.UTF_8);
            Stream<Path> strP = filesContent(dirName).stream().filter(Files::isRegularFile);

            strP.forEach(path -> {
                if (training) {
                    trainList.add(getVectorFromFile(path));
                } else {
                    testList.add(getVectorFromFile(path));
                }
                Matcher m1 = languageShortNameInDirectory.matcher(path.toString());
                m1.find();

                directoryNames = new ArrayList();
                directoryNames.add(m1.group(1));

                int counter = 0;
                for (int i = 0; i < directoryNames.size(); i++) {
                    if (!mapOfLanguages.containsKey(m1)) {
                        mapOfLanguages.put(directoryNames.get(i), counter);
                        counter++;
                    }
                }

                try {
                    Files.lines(path, StandardCharsets.UTF_8)
                            .forEach(line -> {
                                try {
                                    fileWriter.write(line + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static ArrayList<Path> filesContent(String dirName) {
        ArrayList<Path> content = new ArrayList<>();
        try {
            Files.walk(Paths.get(dirName)).forEach(content::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static int getOccurrencesOfStringInArrayList(ArrayList<String> arrayList, String string) {
        int counter = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(string)) {
                counter++;
            }
        }
        return counter;
    }

    public static String[] getVectorFromFile(Path path) {
        File f = path.toFile();
        double[] result = new double[27];
        Map<Character, Integer> charMap = new HashMap<>();
        for (int i = 97; i <= 122; i++) {
            charMap.put((char) i, 0);
        }
        try {
            Files.lines(path, StandardCharsets.UTF_8)
                    .forEach(line -> {
                        for (int i = 0; i < line.toCharArray().length; i++) {
                            char character = line.toCharArray()[i];

                            if (charMap.containsKey(character)) {
                                int value = charMap.get(character);
                                //System.out.println(character + " OLD:" + value);
                                charMap.put(character, value + 1);
                                //System.out.println(value + 1);
                            }
                        }
                    });
            System.out.println(charMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int counter = 0;
        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            //System.out.println(entry.getValue());
            result[counter] = entry.getValue();
            counter += 1;
        }
        return changeArrayDoubleToString(result, f);
    }

    public static String[] changeArrayDoubleToString(double[] d, File f) {
        String[] s = new String[d.length];

        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(d[i]);
        }

        Matcher m1 = languageShortNameInDirectory.matcher(f.toString());
        m1.find();

        s[26] = m1.group(1);
        return s;
    }

    public static ArrayList<String> getDirectoryNames() {
        return directoryNames;
    }

}
