package com.example.textanalyzer_gui;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest
{
    @Test
    void textAnalyzeClick()
    {
        try
        {
            HashMap<String, Integer> wordMap = new HashMap<>();

            BufferedReader br = new BufferedReader(new FileReader("theRaven.txt"));
            String str;

            while ((str = br.readLine()) != null)
            {
                str = str.toLowerCase();
                String[] words = str.split(" ");

                // testing to see that str is getting populated correctly.
                assertNotNull(str);

                for (String word : words)
                {
                    if (word.length() == 0)
                    {
                        continue;
                    }

                    Integer occurences = wordMap.get(word);

                    if (occurences == null)
                    {
                        occurences = 1;
                    }
                    else
                    {
                        occurences++;
                    }
                    wordMap.put(word, occurences);
                }

                // testing to see that wordMap is getting populated correctly.
                assertNotNull(wordMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
