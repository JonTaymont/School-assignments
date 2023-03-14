package com.example.textanalyzer_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Controller
{
    @FXML
    private TextArea resultsTextID;

    @FXML
    private Label displayMessageID;

    @FXML
    protected void textAnalyzeClick()
    {
        try
        {
            HashMap<String, Integer> wordMap = new HashMap<>();

            BufferedReader br = new BufferedReader(new FileReader("theRaven.txt"));
            String str;

            // Read in lines from text file, change text to lowercase, split string into words using space as delimiter,
            // for-each loop to find number of occurrences of each word, update HashMap with the occurrences.
            while ((str = br.readLine()) != null)
            {
                str = str.toLowerCase();
                String[] words = str.split(" ");

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
            }

            resultsTextID.clear();

            displayMessageID.setText("Results are listed above");

            // Reverse the order, limit list to 20 values, print only letters and number of occurrences.
            wordMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .toList()
                    .subList(0, 20)
                    .forEach((k) -> resultsTextID.appendText(k.getKey()
                            .replaceAll("[^a-z]", "") + ": " + k.getValue() + "\n"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
