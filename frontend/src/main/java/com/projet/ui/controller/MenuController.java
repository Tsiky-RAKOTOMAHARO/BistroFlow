package com.projet.ui.controller;

import com.projet.common.dto.MenuDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.MenuViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private TableView<MenuDTO> menuTable;
    @FXML private TableColumn<MenuDTO, String> colId;
    @FXML private TableColumn<MenuDTO, String> colNom;
    @FXML private TableColumn<MenuDTO, Integer> colPrix;

    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField prixField;
    @FXML private Label modeLabel;
    @FXML private Label errorLabel;

    private final MenuViewModel viewModel = new MenuViewModel(AppContext.getMenuService());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("idplat"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomplat"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("pu"));

        // Binding liste + erreur
        menuTable.setItems(viewModel.getMenus());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        // remplit le formulaire & met à jour le ViewModel
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

        viewModel.loadAll();
    }

    @FXML
    private void onSearch() {
        viewModel.search(searchField.getText());
    }

    @FXML
    private void onDelete() {
        MenuDTO selected = viewModel.selectedMenuProperty().get();
        if (selected != null) {
            viewModel.delete(selected.getIdplat());
        }
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