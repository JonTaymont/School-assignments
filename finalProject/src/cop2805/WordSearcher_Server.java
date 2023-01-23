// Jonathan Taymont
// 12/3/22
// Final project
// Objective:
// We are going to build an example server/client where the client sends a request to the server and
// receives a response. The server will represent a word searcher functionality where it loads in a large text
// file and searches for occurrences of a word or phrase within the array of strings. It responds with a list of
// integers which represent each line number that the word/phrase appeared in.
// You will construct two applications: a client and a server. The client will contain a GUI where a user can
// type in a word or phrase and press a button to connect to the server. The client will display the list of
// results into a JList on the GUI. The server will accept connections and process the word search,
// responding with a list of integers for the client to then process.

package cop2805;

import java.util.*;
import java.nio.file.*;
import java.net.*;
import java.io.*;

public class WordSearcher_Server
{
    public static List<Integer> myList;

    public static List<Integer> wordSearcher (String userString)
    {
        List<Integer> myList = new ArrayList<>();

        try
        {
            List<String> hamletFileData = Files.readAllLines(Paths.get("hamlet.txt"));
            hamletFileData.replaceAll(String::toUpperCase);

            userString = userString.toUpperCase();

            for (int i = 0; i < hamletFileData.size(); i++)
            {
                String str = hamletFileData.get(i);

                if(str.contains(userString))
                {
                    myList.add(i);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error- " + e);
        }

        return myList;
    }

    public static void server()
    {
        ServerSocket server = null;

        try
        {
            server = new ServerSocket(1236);
            System.out.println("Port bound. Accepting connections");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

        Socket client = null;
        InputStream input;
        OutputStream output;

        while (true)
        {
            try
            {
                client = server.accept();
                input = client.getInputStream();
                output = client.getOutputStream();

                BufferedReader userInput = new BufferedReader(new InputStreamReader(input));
                String userString = userInput.readLine();
                myList = wordSearcher(userString);

                for (Integer x: myList)
                {
                    String response = x.toString() + "\n";
                    output.write(response.getBytes());
                }

                if (userString.equalsIgnoreCase("shutdown"))
                {
                    System.out.println("\nShutting down...");
                    break;
                }

                client.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (client != null)
                {
                    try
                    {
                        client.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main (String[] args)
    {
        try
        {
            server();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
