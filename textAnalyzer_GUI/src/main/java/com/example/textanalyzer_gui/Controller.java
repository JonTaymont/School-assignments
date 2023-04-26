package com.example.textanalyzer_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

/**
 * Returns a HashMap that contains words from a text file. Controller includes:
 * <ul>
 *     <li> A TextArea for displaying the results.
 *     <li> A Label to guide the user.
 *     <li> The text file.
 * </ul>
 * <p>
 * This class will read in "theRaven.txt" file and present the top 20 words that occurred.
 *
 * @author Jonathan Taymont
 * @version 3.0
 */

public class Controller
{
    @FXML
    private TextArea resultsTextID;

    @FXML
    private Label displayMessageID;

    @FXML
    protected void textAnalyzeClick()
    {
        HashMap<String, Integer> wordMap = new HashMap<>();

        try
        {
            Connection connection;

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/text_analyzer","root","cop2805");

            Statement st = connection.createStatement();
            st.executeUpdate("TRUNCATE TABLE word_occurrences.word ");

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

            String s = "INSERT INTO word_occurrences.word (words, occurrences) VALUES (?,?)";
            PreparedStatement p = connection.prepareStatement(s);

            for(Map.Entry <String, Integer> e : wordMap.entrySet())
            {
                p.setString(1, e.getKey());
                p.setInt(2, e.getValue());
                p.executeUpdate();
            }

            resultsTextID.clear();
            displayMessageID.setText("Results are listed above");

            // Reverse the order, limit list to 20 values, print only letters and number of occurrences.
            s = "SELECT * FROM word_occurrences.word ORDER BY occurrences DESC LIMIT 20";
            p = connection.prepareStatement(s);

            ResultSet results = p.executeQuery();

            while (results.next())
            {
                resultsTextID.appendText(results.getString(1)
                        .replaceAll("[^a-z]", "") + ": " + results.getInt(2) + "\n");
            }
            p.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
