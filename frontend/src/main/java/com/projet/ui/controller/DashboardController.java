package com.projet.ui.controller;

import com.projet.ui.config.AppContext;
import com.projet.ui.viewmodel.DashboardViewModel;
import com.projet.ui.viewmodel.DashboardViewModel.PlatStat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label totalRecetteLabel;
    @FXML private Label totalTablesLabel;
    @FXML private Label totalMenusLabel;
    @FXML private Label errorLabel;

    @FXML private BarChart<String, Number> monthlyBarChart;

    @FXML private TableView<PlatStat> topPlatsTable;
    @FXML private TableColumn<PlatStat, String> colNomPlat;
    @FXML private TableColumn<PlatStat, Integer> colQuantite;

    private final DashboardViewModel viewModel = new DashboardViewModel(
            AppContext.getCommandeService(),
            AppContext.getTableService(),
            AppContext.getMenuService()
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Désactivation des animations pour éviter les exceptions d'affichage JavaFX lors des rafraîchissements
        monthlyBarChart.setAnimated(false);
        monthlyBarChart.setData(viewModel.getMonthlyChartData());

        // Liaison et formatage de la recette totale
        viewModel.recetteTotaleProperty().addListener((obs, oldVal, newVal) ->
                totalRecetteLabel.setText(String.format("%,d", newVal.longValue()) + " Ar"));
        totalRecetteLabel.setText(String.format("%,d", viewModel.recetteTotaleProperty().get()) + " Ar");

        totalTablesLabel.textProperty().bind(viewModel.totalTablesProperty().asString());
        totalMenusLabel.textProperty().bind(viewModel.totalMenusProperty().asString());
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());

        colNomPlat.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().nomplat()));
        colQuantite.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().quantite()).asObject());
        topPlatsTable.setItems(viewModel.getTopPlatsData());

        viewModel.loadDashboard();
    }

    @FXML
    private void onRefresh() {
        viewModel.loadDashboard();
    }
}