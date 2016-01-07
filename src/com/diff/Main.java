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
        BufferedWriter writer = new BufferedWriter(new PrintWriter(diffFilename));

        String line;
        System.out.println("------- ZAWARTOŒÆ PLIKÓW: -------");
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

        System.out.println("\nRó¿nice w plikach:");
        writer.write("Ró¿nice w plikach:\n");
        if (size1 >= size2) {
            for (int i = 0; i < size2; i++) {
                String line1 = list1.get(i);
                String line2 = list2.get(i);
                if (!(list1.contains(line2))) {
                    System.out.print("P1/P2 l." + (i + 1) + "/l." + (i + 1) + ": ");
                    writer.write("P1/P2 l." + (i + 1) + "/l." + (i + 1) + ": ");
                    stringDiff(line1, line2, writer);
                    System.out.println("P1 l." + (i + 1) + ": " + line1);
                    System.out.println("P2 l." + (i + 1) + ": " + line2);
                    addSet.add(line1);
                    addSet.add(line2);
                    writer.write("P1 l." + (i + 1) + ": " + line1 + "\n");
                    writer.write("P2 l." + (i + 1) + ": " + line2 + "\n");
                }
            }
        } else {
            for (int i = 0; i < size1; i++) {
                String line1 = list1.get(i);
                String line2 = list2.get(i);
                if (!(list2.contains(line1))) {
                    System.out.print("P1/P2 l." + (i + 1) + "/l." + (i + 1) + ": ");
                    writer.write("P1/P2 l." + (i + 1) + "/l." + (i + 1) + ": ");
                    stringDiff(line1, line2, writer);
                    System.out.println("P1 l." + (i + 1) + ": " + line1);
                    System.out.println("P2 l." + (i + 1) + ": " + line2);
                    addSet.add(line1);
                    addSet.add(line2);
                    writer.write("P1 l." + (i + 1) + ": " + line1 + "\n");
                    writer.write("P2 l." + (i + 1) + ": " + line2 + "\n");
                }
            }
        }

        System.out.println("\nDodatkowe linie:");
        writer.write("\nDodatkowe linie:\n");
        if (size1 <= size2) {
            for (int i = 0; i < size2; i++) {
                if (!(addSet.contains(list2.get(i)))) {
                    System.out.println("P2 l." + (i + 1) + ": " + list2.get(i));
                    writer.write("P2 l." + (i + 1) + ": " + list2.get(i) + "\n");
                }
            }
        } else {
            for (int i = 0; i < size1; i++) {
                if (!(addSet.contains(list1.get(i)))) {
                    System.out.println("P1 l." + (i + 1) + ": " + list1.get(i));
                    writer.write("P1 l." + (i + 1) + ": " + list1.get(i) + "\n");
                }
            }
        }
        writer.close();
    }

    public static void stringDiff(String in1, String in2, BufferedWriter writer) throws IOException {
        String[] array1 = in1.split(" ");
        String[] array2 = in2.split(" ");

        for (int i = 0; i < array1.length; i++) {
            try {
                if (array1[i].equals(array2[i])) {
                    System.out.print(array1[i] + " ");
                    writer.write(array1[i] + " ");
                } else {
                    System.out.print("\"" + array1[i] + "\" / \"" + array2[i] + "\" ");
                    writer.write("\"" + array1[i] + "\" / \"" + array2[i] + "\" ");
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        writer.write("\n");
    }
}