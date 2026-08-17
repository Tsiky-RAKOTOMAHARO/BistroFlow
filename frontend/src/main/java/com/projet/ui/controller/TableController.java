package com.projet.ui.controller;

import com.projet.common.dto.TableDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.TableViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML private TableView<TableDTO> tableTable;
    @FXML private TableColumn<TableDTO, String> colId;
    @FXML private TableColumn<TableDTO, String> colDesignation;
    @FXML private TableColumn<TableDTO, Boolean> colOccupation;
    @FXML private TableColumn<TableDTO, Void> colActions;

    @FXML private TextField searchField;
    @FXML private TextField designationField;
    @FXML private CheckBox occupationCheckBox;
    @FXML private Label modeLabel;
    @FXML private Label errorLabel;

    private TableDTO editingOriginal;

    private final TableViewModel viewModel = new TableViewModel(AppContext.getTableService());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idtable"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colOccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));

        colOccupation.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : (item ? "Oui" : "Non"));
            }
        });

        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Modifier");
            private final Button deleteBtn = new Button("Supprimer");
            private final HBox box = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> populateForm(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> viewModel.delete(getTableView().getItems().get(getIndex()).getIdtable()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tableTable.setItems(viewModel.getTables());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        viewModel.loadAll();
    }

    private void populateForm(TableDTO item) {
        editingOriginal = item;
        viewModel.selectedTableProperty().set(item);
        designationField.setText(item.getDesignation());
        occupationCheckBox.setSelected(Boolean.TRUE.equals(item.getOccupation()));
        modeLabel.setText("Mode : Modification (" + item.getIdtable() + ")");
    }

    @FXML
    private void onSearch() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.isBlank()) {
            viewModel.errorMessageProperty().set("Saisis un mot-clé pour rechercher.");
            return;
        }
        viewModel.search(keyword.trim());
    }

    @FXML
    private void onResetSearch() {
        searchField.clear();
        viewModel.loadAll();
    }

    @FXML
    private void onNew() {
        editingOriginal = null;
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

        if (editingOriginal != null) {
            dto.setIdtable(editingOriginal.getIdtable());
        }

        viewModel.save(dto);
        onNew();
    }
}