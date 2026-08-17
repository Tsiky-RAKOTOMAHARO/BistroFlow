package com.projet.ui.controller;

import com.projet.common.dto.ReserverDTO;
import com.projet.common.dto.TableDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.ReserverViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReserverController implements Initializable {

    @FXML private TableView<ReserverDTO> reserverTable;
    @FXML private TableColumn<ReserverDTO, String> colId;
    @FXML private TableColumn<ReserverDTO, String> colNomcli;
    @FXML private TableColumn<ReserverDTO, String> colTable;
    @FXML private TableColumn<ReserverDTO, String> colDateReserve;
    @FXML private TableColumn<ReserverDTO, Void> colActions;

    @FXML private TextField searchField;
    @FXML private TextField nomcliField;
    @FXML private ComboBox<TableDTO> tableComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private Label modeLabel;
    @FXML private Label errorLabel;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private ReserverDTO editingOriginal;
    private final ReserverViewModel viewModel = new ReserverViewModel(AppContext.getReserverService());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idreserv"));
        colNomcli.setCellValueFactory(new PropertyValueFactory<>("nomcli"));
        colTable.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getIdtable() != null ? data.getValue().getIdtable() : "-"));

        // Formatage personnalisé de la date et l'heure dans la table
        colDateReserve.setCellValueFactory(data -> {
            LocalDateTime date = data.getValue().getDate_reserve();
            if (date == null) return new SimpleStringProperty("-");
            return new SimpleStringProperty(date.format(DISPLAY_DATE_FORMAT));
        });

        tableComboBox.setConverter(new StringConverter<TableDTO>() {
            @Override
            public String toString(TableDTO table) {
                return table == null ? "" : table.getDesignation();
            }
            @Override
            public TableDTO fromString(String string) {
                return null;
            }
        });

        loadTables();

        reserverTable.setItems(viewModel.getReservations());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        searchField.textProperty().addListener((obs, oldVal, newVal) -> viewModel.search(newVal));

        reserverTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            viewModel.selectedReserverProperty().set(selected);
            if (selected != null) {
                populateForm(selected);
            } else {
                resetFormFields();
            }
        });

        setupActionsColumn();
        viewModel.loadAll();
    }

    private void setupActionsColumn() {
        if (colActions == null) return;

        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Modifier");
            private final Button deleteBtn = new Button("Supprimer");
            private final HBox container = new HBox(5, editBtn, deleteBtn);

            {
                container.setAlignment(Pos.CENTER);

                editBtn.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        reserverTable.getSelectionModel().select(index);
                    }
                });

                deleteBtn.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        ReserverDTO selected = getTableView().getItems().get(index);
                        if (selected != null) {
                            viewModel.delete(selected.getIdreserv());
                            onNew();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
    }

    private void loadTables() {
        try {
            tableComboBox.getItems().setAll(AppContext.getTableService().getAll());
        } catch (Exception e) {
            errorLabel.setText("Impossible de charger les tables : " + e.getMessage());
        }
    }

    private void selectTableById(String idtable) {
        if (idtable == null) {
            tableComboBox.getSelectionModel().clearSelection();
            return;
        }
        tableComboBox.getItems().stream()
                .filter(t -> idtable.equals(t.getIdtable()))
                .findFirst()
                .ifPresentOrElse(
                        t -> tableComboBox.getSelectionModel().select(t),
                        () -> tableComboBox.getSelectionModel().clearSelection());
    }

    private void populateForm(ReserverDTO selected) {
        editingOriginal = selected;
        nomcliField.setText(selected.getNomcli());
        selectTableById(selected.getIdtable());

        LocalDateTime dateReserve = selected.getDate_reserve();
        if (dateReserve != null) {
            datePicker.setValue(dateReserve.toLocalDate());
            timeField.setText(dateReserve.toLocalTime().format(TIME_FORMAT));
        } else {
            datePicker.setValue(null);
            timeField.clear();
        }
        modeLabel.setText("Mode : Modification (" + selected.getIdreserv() + ")");
    }

    private void resetFormFields() {
        editingOriginal = null;
        nomcliField.clear();
        tableComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        timeField.clear();
        modeLabel.setText("Mode : Création");
    }

    @FXML
    private void onSearch() {
        viewModel.search(searchField.getText());
    }

    @FXML
    private void onResetSearch() {
        searchField.clear();
        viewModel.loadAll();
    }

    @FXML
    private void onDelete() {
        ReserverDTO selected = viewModel.selectedReserverProperty().get();
        if (selected != null) {
            viewModel.delete(selected.getIdreserv());
            onNew();
        }
    }

    @FXML
    private void onNew() {
        reserverTable.getSelectionModel().clearSelection();
        viewModel.selectedReserverProperty().set(null);
        resetFormFields();
    }

    @FXML
    private void onSave() {
        String nomcli = nomcliField.getText();
        LocalDate date = datePicker.getValue();
        String timeText = timeField.getText();
        TableDTO selectedTable = tableComboBox.getSelectionModel().getSelectedItem();

        if (nomcli == null || nomcli.isBlank() || date == null || timeText == null || timeText.isBlank()) {
            viewModel.errorMessageProperty().set("Veuillez remplir le client, la date et l'heure.");
            return;
        }

        LocalTime time;
        try {
            time = LocalTime.parse(timeText.trim(), TIME_FORMAT);
        } catch (Exception e) {
            viewModel.errorMessageProperty().set("Heure invalide, format attendu HH:mm.");
            return;
        }

        ReserverDTO dto = new ReserverDTO();
        dto.setNomcli(nomcli.trim());
        dto.setDate_reserve(LocalDateTime.of(date, time));
        dto.setIdtable(selectedTable != null ? selectedTable.getIdtable() : null);

        if (editingOriginal != null) {
            dto.setIdreserv(editingOriginal.getIdreserv());
            dto.setDate_de_reserv(editingOriginal.getDate_de_reserv());
        } else {
            dto.setDate_de_reserv(LocalDateTime.now());
        }

        viewModel.save(dto);
        onNew();
    }
}