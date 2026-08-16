package com.projet.ui.view;

import com.projet.ui.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MenuView {

    private static final String FXML_PATH = "/fxml/menu-view.fxml";

    private final Parent root;
    private final MenuController controller;

    public MenuView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        this.root = loader.load();
        this.controller = loader.getController();
    }

    public Parent getRoot() { return root; }
    public MenuController getController() { return controller; }
}