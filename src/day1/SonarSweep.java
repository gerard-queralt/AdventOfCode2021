package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SonarSweep {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> input = new ArrayList<>();
        File inputFile = new File(new File("src", "day1").getAbsolutePath() + File.separator + "input.txt");
        Scanner in = new Scanner(inputFile);

        while (in.hasNextInt()) { //read input
            input.add(in.nextInt());
        }

        int countLargerThanPrev = 0;
        for(int i = 1; i < input.size(); ++i){
            if(input.get(i) > input.get(i-1))
                ++countLargerThanPrev;
        }

        System.out.println("Part 1: " + countLargerThanPrev);

        int countLargerWindow = 0;
        if(input.size() >= 3){
            ArrayList<Integer> windows = new ArrayList<>(); //Array to avoid repeated operations
            windows.add(input.stream().limit(3).reduce(Integer::sum).get());
            while (input.size() > 3) {
                input = input.stream().skip(1) //pop the first element of the input
                                .collect(Collectors
                                .toCollection(ArrayList::new));

                int currentWindow = input.stream().limit(3).reduce(Integer::sum).get();
                if(currentWindow > windows.get(windows.size() - 1)){
                    ++countLargerWindow;
                }
                windows.add(currentWindow);
            }
            //Simpler (and more boring) version
            /*windows.add(input.get(0) + input.get(1) + input.get(2));
            for (int i = 1; i + 2 < input.size(); i+=1) {
                int currentWindow = input.get(i) + input.get(i + 1) + input.get(i + 2);
                if(currentWindow > windows.get(windows.size() - 1)){
                    ++countLargerWindow;
                }
                windows.add(currentWindow);
            }*/
        }

        System.out.println("Part 2: " + countLargerWindow);
    }
}
