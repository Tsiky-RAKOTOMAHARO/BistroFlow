package com.projet.ui.controller;

import com.projet.common.dto.TableDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.TableViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
                occupationCheckBox.setSelected(selected.getOccupation());
                modeLabel.setText("Mode : Modification (" + selected.getIdtable() + ")");
            } else {
                modeLabel.setText("Mode : Création");
            }
        });

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            viewModel.search(newVal);
        });

        setupActionsColumn();
        viewModel.loadAll();
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Modifier");
            private final Button toggleBtn = new Button("Statut");
            private final Button deleteBtn = new Button("Supprimer");

            {
                editBtn.setOnAction(event -> {
                    TableDTO table = getTableView().getItems().get(getIndex());
                    if (table != null) {
                        tableTable.getSelectionModel().select(table);
                    }
                });

                toggleBtn.setOnAction(event -> {
                    TableDTO table = getTableView().getItems().get(getIndex());
                    if (table != null) {
                        table.setOccupation(!table.getOccupation());
                        viewModel.save(table);
                    }
                });

                deleteBtn.setOnAction(event -> {
                    TableDTO table = getTableView().getItems().get(getIndex());
                    if (table != null) {
                        viewModel.delete(table.getIdtable());
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
                    HBox container = new HBox(5, editBtn, toggleBtn, deleteBtn);
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
            viewModel.errorMessageProperty().set("Veuillez saisir une désignation.");
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