package com.projet.ui.viewmodel;

import com.projet.common.dto.CommandeDTO;
import com.projet.common.dto.LigneCommandeDTO;
import com.projet.common.dto.MenuDTO;
import com.projet.common.dto.TableDTO;
import com.projet.ui.services.CommandeService;
import com.projet.ui.services.MenuService;
import com.projet.ui.services.TableService;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardViewModel {

    private final CommandeService commandeService;
    private final TableService tableService;
    private final MenuService menuService;

    private final LongProperty recetteTotale = new SimpleLongProperty(0);
    private final IntegerProperty totalTables = new SimpleIntegerProperty(0);
    private final IntegerProperty totalMenus = new SimpleIntegerProperty(0);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    private final ObservableList<XYChart.Series<String, Number>> monthlyChartData = FXCollections.observableArrayList();
    private final ObservableList<PlatStat> topPlatsData = FXCollections.observableArrayList();

    public record PlatStat(String nomplat, int quantite) {}

    public DashboardViewModel(CommandeService cs, TableService ts, MenuService ms) {
        this.commandeService = cs;
        this.tableService = ts;
        this.menuService = ms;
    }

    public void loadDashboard() {
        try {
            List<MenuDTO> menus = menuService.getAll();
            List<TableDTO> tables = tableService.getAll();
            List<CommandeDTO> commandes = commandeService.getAll();

            Map<String, MenuDTO> menuMap = menus.stream()
                    .collect(Collectors.toMap(MenuDTO::getIdplat, m -> m, (a, b) -> a));

            totalTables.set(tables.size());
            totalMenus.set((int) menus.stream().filter(m -> Boolean.TRUE.equals(m.isActif())).count());

            long totalRecette = 0;
            Map<String, Integer> platQuantities = new HashMap<>();

            for (CommandeDTO cmd : commandes) {
                if (cmd.getLignes() != null) {
                    for (LigneCommandeDTO ligne : cmd.getLignes()) {
                        MenuDTO menu = menuMap.get(ligne.getIdplat());
                        int pu = (menu != null) ? menu.getPu() : 0;

                        if (Boolean.TRUE.equals(cmd.isPaye())) {
                            totalRecette += (long) pu * ligne.getQuantite();
                        }

                        platQuantities.merge(ligne.getIdplat(), ligne.getQuantite(), Integer::sum);
                    }
                }
            }
            recetteTotale.set(totalRecette);

            List<PlatStat> top10 = platQuantities.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(10)
                    .map(e -> {
                        MenuDTO m = menuMap.get(e.getKey());
                        String nom = (m != null) ? m.getNomplat() : e.getKey();
                        return new PlatStat(nom, e.getValue());
                    })
                    .collect(Collectors.toList());
            topPlatsData.setAll(top10);

            buildMonthlyChart(commandes, menuMap);

            errorMessage.set("");
        } catch (Exception e) {
            errorMessage.set("Erreur lors du calcul des statistiques : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void buildMonthlyChart(List<CommandeDTO> commandes, Map<String, MenuDTO> menuMap) {
        monthlyChartData.clear();

        XYChart.Series<String, Number> seriesRecette = new XYChart.Series<>();
        seriesRecette.setName("Recette Totale");

        XYChart.Series<String, Number> seriesCommandes = new XYChart.Series<>();
        seriesCommandes.setName("Nombre de Commandes");

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRENCH);

        for (int i = 5; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthLabel = month.format(formatter);

            long monthRevenue = 0;
            int monthOrders = 0;

            for (CommandeDTO cmd : commandes) {
                YearMonth cmdMonth = (cmd.getDatecom() != null) ? YearMonth.from(cmd.getDatecom()) : YearMonth.now();
                
                if (cmdMonth.equals(month)) {
                    monthOrders++;
                    if (Boolean.TRUE.equals(cmd.isPaye()) && cmd.getLignes() != null) {
                        for (LigneCommandeDTO l : cmd.getLignes()) {
                            MenuDTO m = menuMap.get(l.getIdplat());
                            monthRevenue += (long) (m != null ? m.getPu() : 0) * l.getQuantite();
                        }
                    }
                }
            }

            seriesRecette.getData().add(new XYChart.Data<>(monthLabel, monthRevenue));
            seriesCommandes.getData().add(new XYChart.Data<>(monthLabel, monthOrders));
        }

        monthlyChartData.addAll(seriesRecette, seriesCommandes);
    }

    public LongProperty recetteTotaleProperty() { return recetteTotale; }
    public IntegerProperty totalTablesProperty() { return totalTables; }
    public IntegerProperty totalMenusProperty() { return totalMenus; }
    public StringProperty errorMessageProperty() { return errorMessage; }
    public ObservableList<XYChart.Series<String, Number>> getMonthlyChartData() { return monthlyChartData; }
    public ObservableList<PlatStat> getTopPlatsData() { return topPlatsData; }
}