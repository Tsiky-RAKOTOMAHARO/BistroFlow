package com.projet.ui.view;

import com.projet.ui.controller.ReserverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ReserverView {

    private static final String FXML_PATH = "/fxml/reserver-view.fxml";

    private final Parent root;
    private final ReserverController controller;

    public ReserverView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        this.root = loader.load();
        this.controller = loader.getController();
    }

    public Parent getRoot() { return root; }
    public ReserverController getController() { return controller; }
}