package com.projet.ui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class DashboardView {

    private Parent root;

    public DashboardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard-view.fxml"));
            root = loader.load();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de dashboard-view.fxml : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Parent getRoot() {
        return root;
    }
}