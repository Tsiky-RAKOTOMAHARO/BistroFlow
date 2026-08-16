package com.projet.ui.controller;

import com.projet.common.dto.TableDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.TableViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML private TableView<TableDTO> tableTable;
    @FXML private TableColumn<TableDTO, String> colId;
    @FXML private TableColumn<TableDTO, String> colDesignation;
    @FXML private TableColumn<TableDTO, Boolean> colOccupation;

    @FXML private TextField searchField;
    @FXML private TextField designationField;
    @FXML private CheckBox occupationCheckBox;
    @FXML private Label modeLabel;
    @FXML private Label errorLabel;

    private final TableViewModel viewModel = new TableViewModel(AppContext.getTableService());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idtable"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colOccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));

        tableTable.setItems(viewModel.getTables());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        tableTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            viewModel.selectedTableProperty().set(selected);
            if (selected != null) {
                designationField.setText(selected.getDesignation());
                occupationCheckBox.setSelected(Boolean.TRUE.equals(selected.getOccupation()));
                modeLabel.setText("Mode : Modification (" + selected.getIdtable() + ")");
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
        TableDTO selected = viewModel.selectedTableProperty().get();
        if (selected != null) {
            viewModel.delete(selected.getIdtable());
        }
    }

    @FXML
    private void onNew() {
        tableTable.getSelectionModel().clearSelection();
        viewModel.selectedTableProperty().set(null);
        designationField.clear();
        occupationCheckBox.setSelected(false);
        modeLabel.setText("Mode : Création");
    }

    @FXML
    private void onSave() {
        String designation = designationField.getText();

        if (designation == null || designation.isBlank()) {
            viewModel.errorMessageProperty().set("Veuillez renseigner la désignation.");
            return;
        }

        TableDTO dto = new TableDTO();
        dto.setDesignation(designation.trim());
        dto.setOccupation(occupationCheckBox.isSelected());

        TableDTO selected = viewModel.selectedTableProperty().get();
        if (selected != null) {
            dto.setIdtable(selected.getIdtable());
        }

        viewModel.save(dto);
        onNew();
    }
}