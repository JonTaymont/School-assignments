// Jonathan Taymont
// 4/23/2023
// CEN-3024C

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application
{
    // Override the start method in the Application class
    @Override
    public void start(Stage primaryStage)
    {
        // Text area for displaying contents
        TextArea ta = new TextArea();
        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        new Thread( () -> {
            try
            {
                // Create a server socket
                Socket socket;
                try (ServerSocket serverSocket = new ServerSocket(1236))
                {
                    Platform.runLater(() ->
                            ta.appendText("Server started at " + new Date() + '\n'));
                    // Listen for a connection request
                    socket = serverSocket.accept();
                }
                // Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());
                while (true)
                {
                    // Receive prime number from the client
                    int primeNumber = inputFromClient.readInt();
                    // Compute prime number
                    boolean valueIsPrime = isPrime(primeNumber);

                    // Send area back to the client
                    outputToClient.writeBoolean(valueIsPrime);
                    Platform.runLater(() -> {
                        ta.appendText("Number received from client to check if it is a prime number: "
                                + primeNumber + '\n');
                    });
                }
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }).start();
    }
    public boolean isPrime(int n)
    {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
