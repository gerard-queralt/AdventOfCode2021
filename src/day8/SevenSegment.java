package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SevenSegment {
    private static int part1() throws FileNotFoundException {
        ArrayList<String[]> inputLines = new ArrayList<>();
        File inputFile = new File(new File("src", "day8").getAbsolutePath() + File.separator + "input.txt");
        Scanner in = new Scanner(inputFile);
        while(in.hasNextLine()){
            String[] splitBar = in.nextLine().split("\\|");
            String[] outputValues = splitBar[1].split(" ");
            inputLines.add(outputValues);
        }
        in.close();
        int countDigitsUniqueSegments = 0;
        for(String[] outputValues : inputLines){
            for(String digit : outputValues){
                int nSegments = digit.length();
                if(nSegments == 2 || nSegments == 3 || nSegments == 4 || nSegments == 7)
                    ++countDigitsUniqueSegments;
            }
        }
        return countDigitsUniqueSegments;
    }

    private static int part2() throws FileNotFoundException {
        ArrayList<String[][]> inputLines = new ArrayList<>();
        File inputFile = new File(new File("src", "day8").getAbsolutePath() + File.separator + "input.txt");
        Scanner in = new Scanner(inputFile);
        while(in.hasNextLine()){
            String[] splitBar = in.nextLine().split("\\|");
            String[] patterns = splitBar[0].split(" ");
            Arrays.sort(patterns, Comparator.comparingInt(String::length)); //sort by length
            String[] outputValues = splitBar[1].split(" ");
            String[][] line = { patterns, outputValues };
            inputLines.add(line);
        }
        in.close();

        int result = 0;
        for(String[][] line : inputLines){
            result += outputOfLine(line);
        }

        return result;
    }

    private static int outputOfLine(String[][] line) {
        Map<Integer, List<String>> foundNumbers = new TreeMap<>();
        String[] patterns = line[0];
        foundNumbers.put(1, splitStringToChars(patterns[0]));
        foundNumbers.put(7, splitStringToChars(patterns[1]));
        foundNumbers.put(4, splitStringToChars(patterns[2]));
        foundNumbers.put(8, splitStringToChars(patterns[9]));
        for(int i = 3; i < 6; ++i){ // 5 segments
            List<String> pattern = splitStringToChars(patterns[i]);
            if(isSublist(pattern, foundNumbers.get(1))){ // 3
                foundNumbers.put(3, pattern);
            }
            else if(isSublist(pattern, listDifference(foundNumbers.get(4), foundNumbers.get(1)))){ // 5
                foundNumbers.put(5, pattern);
            }
            else { // 2
                foundNumbers.put(2, pattern);
            }
        }
        for(int i = 6; i < 9; ++i){ // 6 segments
            List<String> pattern = splitStringToChars(patterns[i]);
            if(isSublist(pattern, foundNumbers.get(4))){ // 9
                foundNumbers.put(9, pattern);
            }
            else if(isSublist(pattern, listDifference(foundNumbers.get(4), foundNumbers.get(1)))){ // 6
                foundNumbers.put(6, pattern);
            }
            else { // 0
                foundNumbers.put(0, pattern);
            }
        }
        String[] output = line[1];
        int n = 3;
        int result = 0;
        for(String outputNumber : output){
            List<String> segmentList = splitStringToChars(outputNumber);
            for(Map.Entry<Integer, List<String>> number : foundNumbers.entrySet()){
                if(equalList(segmentList, number.getValue())){
                    result += number.getKey() * Math.pow(10, n);
                    --n;
                }
            }
        }
        return result;
    }

    private static boolean equalList(List<String> list1, List<String> list2) {
        List<String> list1Copy = new ArrayList<>(list1);
        List<String> list2Copy = new ArrayList<>(list2);
        Collections.sort(list1Copy);
        Collections.sort(list2Copy);
        return list1Copy.equals(list2Copy);
    }

    private static List<String> listDifference(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>(list1);
        result.removeAll(list2);
        return result;
    }

    static boolean isSublist(List<String> list1, List<String> list2) {
        int m = list1.size();
        int n = list2.size();
        int i;
        int j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++)
                if (list2.get(i).equals(list1.get(j)))
                    break;
            if (j == m)
                return false;
        }
        return true;
    }

    private static List<String> splitStringToChars(String pattern) {
        return Arrays.asList(pattern.split("(?!^)"));
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }
}
