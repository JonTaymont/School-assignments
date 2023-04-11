package com.example.textanalyzer_gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * Contains start() and main(). Application includes:
 * <ul>
 *     <li> Size of scene.
 *     <li> Title of GUI.
 *     <li> Icon for GUI.
 * </ul>
 * <p>
 * This class will make the GUI visible with proper size, title, and icon in start() and will launch the GUI in main().
 *
 * @author Jonathan Taymont
 * @version 3.0
 */

public class Application extends javafx.application.Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Text Analyzer");
        Image image = new Image ("/icons/textAnalyzeIcon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
