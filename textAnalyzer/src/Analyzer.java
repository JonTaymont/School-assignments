// Jonathan Taymont
// 2/6/23
// SDLC Assignment
// Objective:
// Write a text analyzer that reads a file and outputs statistics about that file.
// It should output the word frequencies of all words in the file, sorted by the most frequently used word.
// The output should be a set of pairs, each pair containing a word and how many times it occurred in the file.

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Analyzer
{
    public static void main(String[] args)
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

            // Reverse the order, limit list to 20 values, print only letters and number of occurrences.
            wordMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toList())
                    .subList(0,20)
                    .forEach((k) -> System.out.println(k.getKey()
                            .replaceAll("[^a-z]", "") + ": " + k.getValue()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
