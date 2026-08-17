package com.projet.ui.controller;

import com.projet.ui.navigation.NavigationManager;
import com.projet.ui.view.CommandeView;
import com.projet.ui.view.DashboardView;
import com.projet.ui.view.MenuView;
import com.projet.ui.view.ReserverView;
import com.projet.ui.view.TableView;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainLayoutController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        NavigationManager.getInstance().setContentArea(contentArea);
        showDashboardView(); // Définie comme vue d'accueil par défaut
    }

    @FXML
    private void showDashboardView() {
        try {
            DashboardView dashboardView = new DashboardView();
            NavigationManager.getInstance().navigateTo(dashboardView.getRoot());
        } catch (Exception e) {
            System.err.println("Erreur lors de la navigation vers le Tableau de Bord : " + e.getMessage());
            e.printStackTrace();
        }
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
        try {
            TableView tableView = new TableView();
            NavigationManager.getInstance().navigateTo(tableView.getRoot());
        } catch (Exception e) {
            System.err.println("Erreur lors de la navigation vers la vue Tables : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void showReservationsView() {
        try {
            ReserverView reserverView = new ReserverView();
            NavigationManager.getInstance().navigateTo(reserverView.getRoot());
        } catch (Exception e) {
            System.err.println("Erreur lors de la navigation vers la vue Réservations : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void showCommandesView() {
        try {
            CommandeView commandeView = new CommandeView();
            NavigationManager.getInstance().navigateTo(commandeView.getRoot());
        } catch (Exception e) {
            System.err.println("Erreur lors de la navigation vers la vue Commandes : " + e.getMessage());
            e.printStackTrace();
        }
    }
}