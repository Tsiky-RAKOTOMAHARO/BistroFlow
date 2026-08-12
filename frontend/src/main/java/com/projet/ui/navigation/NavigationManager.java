package com.projet.ui.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class NavigationManager {

    private static NavigationManager instance;
    private StackPane contentArea;

    private NavigationManager() {}

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setContentArea(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    /**
     * Charge dynamiquement un fichier FXML dans le conteneur principal.
     * @param fxmlPath chemin relatif vers le FXML dans resources (ex: "/fxml/tables-view.fxml")
     */
    public void navigateTo(String fxmlPath) {
        if (contentArea == null) {
            throw new IllegalStateException("Le conteneur contentArea n'a pas été initialisé.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Erreur de chargement de la vue FXML : " + fxmlPath);
            e.printStackTrace();
        }
    }
}