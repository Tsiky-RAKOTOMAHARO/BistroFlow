package com.projet.ui.view;

import com.projet.ui.controller.CommandeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CommandeView {

    private static final String FXML_PATH = "/fxml/commande-view.fxml";

    private final Parent root;
    private final CommandeController controller;

    public CommandeView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        this.root = loader.load();
        this.controller = loader.getController();
    }

    public Parent getRoot() { return root; }
    public CommandeController getController() { return controller; }
}