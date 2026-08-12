package com.projet.ui.view;

import com.projet.ui.navigation.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainLayoutController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        // Enregistrer le conteneur principal dans le NavigationManager
        NavigationManager.getInstance().setContentArea(contentArea);
    }

    @FXML
    private void showTablesView() {
        NavigationManager.getInstance().navigateTo("/fxml/tables-view.fxml");
    }

    @FXML
    private void showMenusView() {
        // Sera lié plus tard
        System.out.println("Navigation vers la vue Menus");
    }

    @FXML
    private void showReservationsView() {
        // Sera lié plus tard
        System.out.println("Navigation vers la vue Réservations");
    }

    @FXML
    private void showCommandesView() {
        // Sera lié plus tard
        System.out.println("Navigation vers la vue Commandes");
    }
}