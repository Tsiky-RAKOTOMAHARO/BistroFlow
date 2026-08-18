package com.projet.ui.controller;

import com.projet.common.dto.CommandeDTO;
import com.projet.common.dto.LigneCommandeDTO;
import com.projet.common.dto.MenuDTO;
import com.projet.common.dto.TableDTO;
import com.projet.ui.config.AppContext;
import com.projet.ui.services.PdfReceiptService;
import com.projet.ui.viewmodel.CommandeViewModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CommandeController implements Initializable {

    private static class LigneAffichage {
        String idplat;
        String nomplat;
        int pu;
        int quantite;

        LigneAffichage(String idplat, String nomplat, int pu, int quantite) {
            this.idplat = idplat;
            this.nomplat = nomplat;
            this.pu = pu;
            this.quantite = quantite;
        }
    }

    @FXML private TableView<CommandeDTO> commandeTable;
    @FXML private TableColumn<CommandeDTO, String> colId;
    @FXML private TableColumn<CommandeDTO, String> colNomcli;
    @FXML private TableColumn<CommandeDTO, String> colTypecom;
    @FXML private TableColumn<CommandeDTO, Boolean> colPaye;
    @FXML private TableColumn<CommandeDTO, Void> colActions;

    @FXML private TextField searchField;
    @FXML private TextField nomcliField;
    @FXML private ComboBox<String> typecomComboBox;
    @FXML private ComboBox<TableDTO> tableComboBox;
    @FXML private ComboBox<MenuDTO> platComboBox;
    @FXML private TextField quantiteField;
    @FXML private Label modeLabel;
    @FXML private Label totalLabel;
    @FXML private Label errorLabel;

    @FXML private TableView<LigneAffichage> lignesTable;
    @FXML private TableColumn<LigneAffichage, String> colLignePlat;
    @FXML private TableColumn<LigneAffichage, Integer> colLigneQuantite;
    @FXML private TableColumn<LigneAffichage, Integer> colLignePu;
    @FXML private TableColumn<LigneAffichage, Void> colLigneActions;

    private final ObservableList<LigneAffichage> currentLignes = FXCollections.observableArrayList();
    private List<MenuDTO> menusDisponibles = List.of();

    private CommandeDTO editingOriginal;
    private final CommandeViewModel viewModel = new CommandeViewModel(AppContext.getCommandeService());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idcom"));
        colNomcli.setCellValueFactory(new PropertyValueFactory<>("nomcli"));
        colTypecom.setCellValueFactory(new PropertyValueFactory<>("typecom"));
        colPaye.setCellValueFactory(new PropertyValueFactory<>("paye"));

        colPaye.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : (item ? "Oui" : "Non"));
            }
        });

        commandeTable.setItems(viewModel.getCommandes());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        searchField.textProperty().addListener((obs, oldVal, newVal) -> viewModel.search(newVal));

        commandeTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                populateForm(selected);
            } else {
                resetFormFields();
            }
        });

        typecomComboBox.setItems(FXCollections.observableArrayList("sur_place", "emporter"));
        typecomComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(String value) {
                if ("sur_place".equals(value)) return "Sur place";
                if ("emporter".equals(value)) return "À emporter";
                return "";
            }
            @Override
            public String fromString(String string) { return null; }
        });
        typecomComboBox.valueProperty().addListener((obs, old, val) -> {
            boolean surPlace = "sur_place".equals(val);
            tableComboBox.setDisable(!surPlace);
            if (!surPlace) tableComboBox.getSelectionModel().clearSelection();
        });

        tableComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TableDTO table) { return table == null ? "" : table.getDesignation(); }
            @Override
            public TableDTO fromString(String string) { return null; }
        });

        platComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(MenuDTO menu) { return menu == null ? "" : menu.getNomplat() + " (" + menu.getPu() + ")"; }
            @Override
            public MenuDTO fromString(String string) { return null; }
        });

        colLignePlat.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().nomplat));
        colLigneQuantite.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().quantite).asObject());
        colLignePu.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().pu).asObject());

        colLigneActions.setCellFactory(col -> new TableCell<>() {
            private final Button removeBtn = new Button("X");
            {
                removeBtn.setOnAction(e -> {
                    currentLignes.remove(getIndex());
                    recalculerTotal();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeBtn);
            }
        });

        lignesTable.setItems(currentLignes);

        setupActionsColumn();
        chargerListesReference();
        viewModel.loadAll();
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Modifier");
            private final Button payerBtn = new Button("Payer");
            private final Button printBtn = new Button("Imprimer");
            private final Button deleteBtn = new Button("Supprimer");

            {
                editBtn.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        commandeTable.getSelectionModel().select(index);
                    }
                });

                payerBtn.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        CommandeDTO commande = getTableView().getItems().get(index);
                        if (commande != null) {
                            commande.setPaye(true);
                            viewModel.save(commande);
                        }
                    }
                });

                printBtn.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        CommandeDTO commande = getTableView().getItems().get(index);
                        if (commande != null) {
                            handlePrintReceipt(commande);
                        }
                    }
                });

                deleteBtn.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        CommandeDTO commande = getTableView().getItems().get(index);
                        if (commande != null) {
                            viewModel.delete(commande.getIdcom());
                            onNew();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CommandeDTO commande = getTableView().getItems().get(getIndex());
                    if (commande != null) {
                        boolean isPaye = Boolean.TRUE.equals(commande.isPaye());
                        payerBtn.setVisible(!isPaye);
                        payerBtn.setManaged(!isPaye);
                    }
                    HBox container = new HBox(5, editBtn, payerBtn, printBtn, deleteBtn);
                    container.setAlignment(Pos.CENTER);
                    setGraphic(container);
                }
            }
        });
    }

    private void handlePrintReceipt(CommandeDTO commande) {
        if (commande == null) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le reçu PDF");
        fileChooser.setInitialFileName("recu_commande_" + (commande.getIdcom() != null ? commande.getIdcom() : "ticket") + ".pdf");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers PDF (*.pdf)", "*.pdf")
        );

        File file = fileChooser.showSaveDialog(commandeTable.getScene().getWindow());
        if (file != null) {
            try {
                // Map d'association rapide ID -> MenuDTO pour résoudre les noms et prix des plats
                Map<String, MenuDTO> menuMap = menusDisponibles.stream()
                        .collect(Collectors.toMap(MenuDTO::getIdplat, m -> m, (a, b) -> a));

                PdfReceiptService.generateReceipt(commande, menuMap, file);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Impression réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le reçu PDF a été généré avec succès :\n" + file.getAbsolutePath());
                alert.showAndWait();
            } catch (Exception e) {
                viewModel.errorMessageProperty().set("Erreur lors de la génération du PDF : " + e.getMessage());
            }
        }
    }

    private void chargerListesReference() {
        try {
            tableComboBox.getItems().setAll(AppContext.getTableService().getAll());
            menusDisponibles = AppContext.getMenuService().getAll().stream()
                    .filter(m -> Boolean.TRUE.equals(m.isActif()))
                    .collect(Collectors.toList());
            platComboBox.getItems().setAll(menusDisponibles);
        } catch (Exception e) {
            errorLabel.setText("Impossible de charger les données de référence : " + e.getMessage());
        }
    }

    private void selectTableById(String idtable) {
        tableComboBox.getItems().stream()
                .filter(t -> t.getIdtable().equals(idtable))
                .findFirst()
                .ifPresentOrElse(
                        t -> tableComboBox.getSelectionModel().select(t),
                        () -> tableComboBox.getSelectionModel().clearSelection());
    }

    private MenuDTO trouverMenu(String idplat) {
        return menusDisponibles.stream()
                .filter(m -> m.getIdplat().equals(idplat))
                .findFirst()
                .orElse(null);
    }

    private void recalculerTotal() {
        int total = currentLignes.stream().mapToInt(l -> l.pu * l.quantite).sum();
        totalLabel.setText("Total : " + total);
    }

    private void populateForm(CommandeDTO commande) {
        editingOriginal = commande;
        nomcliField.setText(commande.getNomcli());
        typecomComboBox.setValue(commande.getTypecom());
        selectTableById(commande.getIdtable());

        currentLignes.clear();
        if (commande.getLignes() != null) {
            for (LigneCommandeDTO ligne : commande.getLignes()) {
                MenuDTO menu = trouverMenu(ligne.getIdplat());
                String nom = menu != null ? menu.getNomplat() : ligne.getIdplat();
                int pu = menu != null ? menu.getPu() : 0;
                currentLignes.add(new LigneAffichage(ligne.getIdplat(), nom, pu, ligne.getQuantite()));
            }
        }
        recalculerTotal();
        modeLabel.setText("Mode : Modification (" + commande.getIdcom() + ")");
    }

    private void resetFormFields() {
        editingOriginal = null;
        nomcliField.clear();
        typecomComboBox.getSelectionModel().clearSelection();
        tableComboBox.getSelectionModel().clearSelection();
        currentLignes.clear();
        recalculerTotal();
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
    private void onNew() {
        commandeTable.getSelectionModel().clearSelection();
        resetFormFields();
    }

    @FXML
    private void onAjouterLigne() {
        MenuDTO plat = platComboBox.getSelectionModel().getSelectedItem();
        String quantiteText = quantiteField.getText();

        if (plat == null || quantiteText == null || quantiteText.isBlank()) {
            viewModel.errorMessageProperty().set("Sélectionne un plat et une quantité.");
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteText.trim());
        } catch (NumberFormatException e) {
            viewModel.errorMessageProperty().set("Quantité invalide.");
            return;
        }

        if (quantite <= 0) {
            viewModel.errorMessageProperty().set("La quantité doit être supérieure à 0.");
            return;
        }

        currentLignes.stream()
                .filter(l -> l.idplat.equals(plat.getIdplat()))
                .findFirst()
                .ifPresentOrElse(
                        existing -> existing.quantite += quantite,
                        () -> currentLignes.add(new LigneAffichage(plat.getIdplat(), plat.getNomplat(), plat.getPu(), quantite)));

        lignesTable.refresh();
        recalculerTotal();
        quantiteField.clear();
    }

    @FXML
    private void onSave() {
        String nomcli = nomcliField.getText();
        String typecom = typecomComboBox.getValue();
        TableDTO table = tableComboBox.getSelectionModel().getSelectedItem();

        if (nomcli == null || nomcli.isBlank() || typecom == null) {
            viewModel.errorMessageProperty().set("Renseigne le client et le type de commande.");
            return;
        }

        if ("sur_place".equals(typecom) && table == null) {
            viewModel.errorMessageProperty().set("Une table est requise pour une commande sur place.");
            return;
        }

        if (currentLignes.isEmpty()) {
            viewModel.errorMessageProperty().set("Ajoute au moins une ligne à la commande.");
            return;
        }

        CommandeDTO dto = new CommandeDTO();
        dto.setNomcli(nomcli.trim());
        dto.setTypecom(typecom);
        dto.setIdtable("sur_place".equals(typecom) ? table.getIdtable() : null);
        dto.setPaye(editingOriginal != null && editingOriginal.isPaye());

        if (editingOriginal != null) {
            dto.setIdcom(editingOriginal.getIdcom());
        }

        dto.setLignes(currentLignes.stream()
                .map(l -> {
                    LigneCommandeDTO ligneDto = new LigneCommandeDTO();
                    ligneDto.setIdplat(l.idplat);
                    ligneDto.setQuantite(l.quantite);
                    return ligneDto;
                })
                .collect(Collectors.toList()));

        viewModel.save(dto);
        onNew();
    }
}