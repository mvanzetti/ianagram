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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestDictionaryTool {

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

        System.out.println("Press 'Enter' to start reading from dictionary.");


        try {

            reader.readLine();

        } catch (Exception e) {

            e.printStackTrace();
        }

        DictionaryTool iDictionary = new DictionaryTool("dictionary/dictionary.dic");

        System.out.println("Anagrams for " + testString + " : " + iDictionary.CatchAnagrams(testString));

        System.out.println("Bye!");

    }
}

