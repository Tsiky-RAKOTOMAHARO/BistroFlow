package com.projet.ui.controller;

import com.projet.common.dto.MenuDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.MenuViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private TableView<MenuDTO> menuTable;
    @FXML private TableColumn<MenuDTO, String> colId;
    @FXML private TableColumn<MenuDTO, String> colNom;
    @FXML private TableColumn<MenuDTO, Integer> colPrix;
    @FXML private TableColumn<MenuDTO, Void> colActions;

    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField prixField;
    @FXML private Label modeLabel;
    @FXML private Label errorLabel;

    private final MenuViewModel viewModel = new MenuViewModel(AppContext.getMenuService());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idplat"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomplat"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("pu"));

        menuTable.setItems(viewModel.getMenus());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        menuTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            viewModel.selectedMenuProperty().set(selected);
            if (selected != null) {
                nomField.setText(selected.getNomplat());
                prixField.setText(String.valueOf(selected.getPu()));
                modeLabel.setText("Mode : Modification (" + selected.getIdplat() + ")");
            } else {
                modeLabel.setText("Mode : Création");
            }
        });

        setupActionsColumn();
        viewModel.loadAll();
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Modifier");
            private final Button deleteBtn = new Button("Supprimer");

            {
                editBtn.setOnAction(event -> {
                    MenuDTO menu = getTableView().getItems().get(getIndex());
                    if (menu != null) {
                        menuTable.getSelectionModel().select(menu);
                    }
                });

                deleteBtn.setOnAction(event -> {
                    MenuDTO menu = getTableView().getItems().get(getIndex());
                    if (menu != null) {
                        viewModel.delete(menu.getIdplat());
                        onNew();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(5, editBtn, deleteBtn);
                    container.setAlignment(Pos.CENTER);
                    setGraphic(container);
                }
            }
        });
    }

    @FXML
    private void onSearch() {
        viewModel.search(searchField.getText());
    }

    @FXML
    private void onNew() {
        menuTable.getSelectionModel().clearSelection();
        viewModel.selectedMenuProperty().set(null);
        nomField.clear();
        prixField.clear();
        modeLabel.setText("Mode : Création");
    }

    @FXML
    private void onSave() {
        String nom = nomField.getText();
        String prixText = prixField.getText();

        if (nom == null || nom.isBlank() || prixText == null || prixText.isBlank()) {
            viewModel.errorMessageProperty().set("Veuillez remplir tous les champs du formulaire.");
            return;
        }

        try {
            int prix = Integer.parseInt(prixText.trim());

            MenuDTO dto = new MenuDTO();
            dto.setNomplat(nom.trim());
            dto.setPu(prix);

            MenuDTO selected = viewModel.selectedMenuProperty().get();
            if (selected != null) {
                dto.setIdplat(selected.getIdplat());
            }

            viewModel.save(dto);
            onNew();

        } catch (NumberFormatException e) {
            viewModel.errorMessageProperty().set("Le prix doit être un nombre entier valide.");
        }
    }
}