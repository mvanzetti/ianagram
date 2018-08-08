/**
 * @module Dictionary.java
 * @description Dictionary tool
 * @creation 24/03/2012
 * @revision
 * @author manuel.vanzetti
 * @notes Purpose: construct a usable dictionary from a list of words
 */

package tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;

public class DictionaryTool {

    private String _filePath;

    public String getFilePath() {

        return _filePath;
    }

    private HashMap<String, String> _dictionary;

    public HashMap<String, String> getDictionary() {

        return _dictionary;
    }


    public DictionaryTool(String filePath) {

        _filePath = filePath;
        _dictionary = DictionaryRead();

    }

    private String _inputString;
    private String _alphabetizedInputString;

    public String CatchAnagrams(String InputString) {

        _inputString = InputString;
        _alphabetizedInputString = Alphabetize(_inputString);

        if (_dictionary.containsKey(_alphabetizedInputString)) {

            return _dictionary.get(_alphabetizedInputString);
        } else {
            return "";
        }
    }

    private String Alphabetize(String s) {

        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }

    private HashMap<String, String> DictionaryRead() {

        HashMap<String, String> d = new HashMap<String, String>();

        try {

            FileInputStream fStream = new FileInputStream(_filePath);
            DataInputStream in = new DataInputStream(fStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            while ((strLine = br.readLine()) != null) {

                String a = Alphabetize(strLine);

                if (d.containsKey(a)) {

                    String v = d.get(a);
                    String newValue = v + "," + strLine;
                    d.put(a, newValue);
                } else {

                    d.put(a, strLine);
                }

            }
            //Close the input stream
            in.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return d;

    }
}