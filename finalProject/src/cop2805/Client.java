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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client
{
    public static void constructGUI()
    {
        try
        {
            JFrame.setDefaultLookAndFeelDecorated(true);
            MyFrame frame = new MyFrame();
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main (String[] args)
    {
        try
        {
            SwingUtilities.invokeLater(Client::constructGUI);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class MyFrame extends JFrame
{
    public DefaultListModel<Integer> listModel = new DefaultListModel<>();
    public JTextField wordSearch = new JTextField();

    public MyFrame()
    {
        super();

        try
        {
            Init();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // GUI Build
    private void Init()
    {
        try
        {
            // Make JFrame and set layout.
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Word Search");
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

            int frameWidth = 300;
            int frameHeight = 200;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            this.setBounds
                    (
                            (int) (screenSize.getWidth() / 2) - frameWidth,
                            (int) (screenSize.getHeight() / 2) - frameHeight,
                            frameWidth,
                            frameHeight
                    );

            // Add JPanel 1 and 2.
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            this.add(panel1);
            this.add(panel2);

            // Set layout for panel 1.
            panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            panel1.setLayout(new GridLayout(1, 2));

            // Add JTextField and JLabel to panel 1.
            panel1.add(new JLabel("Word to Search For:"));
            panel1.add(wordSearch = new JTextField());

            // Set layout for panel 2.
            panel2.setLayout(new GridLayout(1, 2));

            // Add JList and JScrollPane for panel 2.
            JList<Integer> list = new JList<>(listModel);
            panel2.add(new JLabel("Response:"));
            JScrollPane scrollableTextArea = new JScrollPane(list);
            scrollableTextArea.createVerticalScrollBar();
            panel2.add(scrollableTextArea);

            // Make transmit button.
            JButton transmit;
            this.add(transmit = new JButton("Transmit"));
            transmit.addActionListener(new MyButtonListener(this));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class MyButtonListener implements ActionListener
{
    MyFrame fr;

    public MyButtonListener (MyFrame frame)
    {
        fr = frame;
    }

    public void actionPerformed (ActionEvent e)
    {
        try
        {
            // Clear the JList in the GUI.
            fr.listModel.clear();

            // Read the string from the text field.
            String getTextValue = fr.wordSearch.getText() + "\n";

            // Open a socket to the server
            Socket connection = new Socket("192.168.1.97", 1236);

            InputStream input = connection.getInputStream();
            OutputStream output = connection.getOutputStream();

            BufferedReader userInput = new BufferedReader(new InputStreamReader(input));

            // Send the string to the server.
            output.write(getTextValue.getBytes());

            // Read the results from the server.
            String response = "";

            while (response != null)
            {
                response = userInput.readLine();
                if (response != null)
                    fr.listModel.addElement(Integer.parseInt(response));
            }

            output.flush();

            // Close the socket to the server.
            if (!connection.isClosed())
                connection.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
