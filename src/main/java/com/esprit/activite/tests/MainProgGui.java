package com.esprit.activite.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainProgGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws IOException { {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Principale.fxml"));
        Parent root= loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout ma");
        primaryStage.show();
    }
    }
}