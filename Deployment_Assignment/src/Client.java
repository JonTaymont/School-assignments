// Jonathan Taymont
// 4/23/2023
// CEN-3024C

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application
{
    // IO streams
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    // Override the start method in the Application class
    @Override
    public void start(Stage primaryStage)
    {
        // Panel p to hold the label and text field
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a number to check if it is prime: "));
        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);
        BorderPane mainPane = new BorderPane();
        // Text area to display contents
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);
        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        tf.setOnAction(e -> {
            try
            {
                // Get the primeNumber from the text field
                int primeNumber = Integer.parseInt(tf.getText().trim());
                // Send the primeNumber to the server
                toServer.writeInt(primeNumber);
                toServer.flush();
                // Get primeCheck from the server
                boolean primeCheck = fromServer.readBoolean();
                // Display to the text primeCheck
                ta.appendText("Number: " + primeNumber + ((primeCheck) ? " is " : " is not ") + "prime\n");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });
        try
        {
            // Create a socket to connect to the server
            Socket socket = new Socket("127.0.0.1", 1236);
            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());
            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex)
        {
            ta.appendText(ex.toString() + '\n');
        }
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
