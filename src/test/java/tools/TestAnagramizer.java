/**
 * @module TestAnagramizer.java
 * @description Anagram algorithm test tool
 * @creation 23/11/2011
 * @revision 03/01/2012
 * @author manuel.vanzetti
 * @notes Here one can tests the com.tools.anagramizer functionalities
 * and verify the speed of the algorithm that lies beyond...
 */

package tools;

import java.io.*;

public class TestAnagramizer {


    public static void main(String args[]) throws IOException {

        String testString = "";

        System.out.println("Welcome to the LadyBird Labs!");
        System.out.println("Here you can test 'Anagramizer'.");
        System.out.println("");

        System.out.println("Type a word, the press 'Enter'");

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        try {

            testString = reader.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("You typed '" + testString + "'");


        Anagramizer iAnagram = new Anagramizer(testString);

        System.out.println("About " + Math.round(iAnagram.getDistinctPermutationsNumber()) + " distinct permutations predicted.");

        System.out.println("Press 'Enter' to start.");

        try {

            reader.readLine();

        } catch (Exception e) {

            e.printStackTrace();
        }

        //	start algorithm timer
        iAnagram.startTimeCounterMS();

        String anagramizedString;

        do {

            int count = iAnagram.getAnagramCounter();

            anagramizedString = iAnagram.getAnagram();

            //	comment the next row if you don't want to print the permutations
            System.out.println(count + ": " + anagramizedString);
        }

        while (iAnagram.NextAnagram());

        //	stop algorithm timer
        iAnagram.stopTimeCounterMS();

        System.out.println(iAnagram.getTimeCounterMS() + " ms elapsed.");
        System.out.println((iAnagram.getAnagramCounter() - 1) + " distinct permutations computed.");
        System.out.println("Bye!");

    }
}

