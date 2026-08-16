package com.projet.ui.navigation;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

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

    
    public void navigateTo(Parent view) {
        if (contentArea == null) {
            throw new IllegalStateException("Le conteneur contentArea n'a pas été initialisé.");
        }
        contentArea.getChildren().setAll(view);
    }
}