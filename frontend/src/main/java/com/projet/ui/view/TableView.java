package com.projet.ui.view;

import com.projet.ui.controller.TableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class TableView {

    private static final String FXML_PATH = "/fxml/table-view.fxml";

    private final Parent root;
    private final TableController controller;

    public TableView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        this.root = loader.load();
        this.controller = loader.getController();
    }

    public Parent getRoot() { return root; }
    public TableController getController() { return controller; }
}