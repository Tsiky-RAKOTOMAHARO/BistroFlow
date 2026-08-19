package com.projet.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-layout.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 700); 
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        primaryStage.getIcons().add(
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bistro.png")))
        );

        primaryStage.setTitle("BistroFlow");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(700);
    }

    public static void main(String[] args) {
        launch(args);
    }
}