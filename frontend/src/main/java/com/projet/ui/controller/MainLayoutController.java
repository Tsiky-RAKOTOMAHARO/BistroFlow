package com.projet.ui.controller;

import com.projet.ui.navigation.NavigationManager;
import com.projet.ui.view.MenuView;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainLayoutController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        NavigationManager.getInstance().setContentArea(contentArea);
        showMenusView();
    }

    @FXML
    private void showMenusView() {
        try {
            MenuView menuView = new MenuView();
            NavigationManager.getInstance().navigateTo(menuView.getRoot());
        } catch (Exception e) {
            System.err.println("Erreur lors de la navigation vers la vue Menus : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void showTablesView() {
        System.out.println("Vue Tables pas encore branchée");
    }

    @FXML
    private void showReservationsView() {
        System.out.println("Vue Réservations miandryyyy");
    }

    @FXML
    private void showCommandesView() {
        System.out.println("Vue Commandes miandryyyy");
    }
}