/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 *
 * @author cecce
 */
public class ClimatonixFX extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize Parent class
        Parent root = FXMLLoader.load(getClass().getResource("ClimatonixFXML.fxml"));
        
        // Create a new JavaFX scene
        Scene scene = new Scene(root, 592, 390);
        
        // Configure stage settings
        primaryStage.setTitle("Climatonix");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:icon.png"));
        
        // Display application
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
