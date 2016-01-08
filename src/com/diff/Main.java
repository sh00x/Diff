package com.diff;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException {
        String filepath1 = "C:\\Users\\\u0141ukasz\\Documents\\Pliki diff\\plik1.txt";
        String filepath2 = "C:\\Users\\\u0141ukasz\\Documents\\Pliki diff\\plik2.txt";
        diff(filepath1, filepath2);
    }

    public static void diff(String input1, String input2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(input1));
        BufferedReader reader2 = new BufferedReader(new FileReader(input2));
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        HashSet<String> addSet = new HashSet<>();

        String diffFilename = "diff_" + Paths.get(input1).getFileName();
        PrintWriter writer = new PrintWriter(diffFilename);

        String line;
        System.out.println("------- ZAWARTOŚĆ PLIKÓW: -------");
        System.out.println("Plik 1:");
        while ((line = reader1.readLine()) != null) {
            list1.add(line);
            System.out.println(line);
        }

        System.out.println("\nPlik 2:");
        while ((line = reader2.readLine()) != null) {
            list2.add(line);
            System.out.println(line);
        }
        System.out.println("---------------------------------");
        reader1.close();
        reader2.close();

        int size1 = list1.size();
        int size2 = list2.size();

        for (int i = 0; i < size1; i++) {
            String test = list1.get(i);
            if (list1.contains(test) && list2.contains(test)) {
                addSet.add(test);
            }
        }

        for (int i = 0; i < size2; i++) {
            String test = list2.get(i);
            if (list1.contains(test) && list2.contains(test)) {
                addSet.add(test);
            }
        }

        printlnSave("Różnice w plikach:", writer);
        if (size1 >= size2) {
            for (int i = 0; i < size2; i++) {
                String line1 = list1.get(i);
                String line2 = list2.get(i);
                if (!(list1.contains(line2))) {
                    printlnSave("P1/P2 l." + (i + 1) + "/l." + (i + 1) + ": ", writer);
                    stringDiff(line1, line2, writer);
                    printlnSave("P1 l." + (i + 1) + ": " + line1, writer);
                    printlnSave("P2 l." + (i + 1) + ": " + line2, writer);
                    addSet.add(line1);
                    addSet.add(line2);
                }
            }
        } else {
            for (int i = 0; i < size1; i++) {
                String line1 = list1.get(i);
                String line2 = list2.get(i);
                if (!(list2.contains(line1))) {
                    printSave("P1/P2 l." + (i + 1) + "/l." + (i + 1) + ": ", writer);
                    stringDiff(line1, line2, writer);
                    printlnSave("P1 l." + (i + 1) + ": " + line1, writer);
                    printlnSave("P2 l." + (i + 1) + ": " + line2, writer);
                    addSet.add(line1);
                    addSet.add(line2);
                }
            }
        }

        printlnSave("\nDodatkowe linie:", writer);
        if (size1 <= size2) {
            for (int i = 0; i < size2; i++) {
                if (!(addSet.contains(list2.get(i)))) {
                    printlnSave("P2 l." + (i + 1) + ": " + list2.get(i), writer);
                }
            }
        } else {
            for (int i = 0; i < size1; i++) {
                if (!(addSet.contains(list1.get(i)))) {
                    printlnSave("P1 l." + (i + 1) + ": " + list1.get(i), writer);
                }
            }
        }
        writer.close();
    }

    public static void stringDiff(String in1, String in2, PrintWriter writer) throws IOException {
        String[] array1 = in1.split(" ");
        String[] array2 = in2.split(" ");

        for (int i = 0; i < array1.length; i++) {
            try {
                if (array1[i].equals(array2[i])) {
                    printSave(array1[i] + " ", writer);
                } else {
                    printSave("\"" + array1[i] + "\" / \"" + array2[i] + "\" ", writer);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        printlnSave("", writer);
    }

    private static void printSave(String input, PrintWriter writer) {
        System.out.print(input);
        writer.write(input);
    }

    private static void printlnSave(String input, PrintWriter writer) throws IOException {
        System.out.println(input);
        writer.println(input);
    }
}