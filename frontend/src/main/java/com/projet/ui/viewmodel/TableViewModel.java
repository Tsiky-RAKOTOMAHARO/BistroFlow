package com.projet.ui.viewmodel;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import com.projet.common.dto.TableDTO;
import com.projet.ui.services.TableService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class TableViewModel {

    private final TableService tableService;

    private final ObservableList<TableDTO> tables = FXCollections.observableArrayList();
    private final ObjectProperty<TableDTO> selectedTable = new SimpleObjectProperty<>();
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public TableViewModel(TableService tableService) {
        this.tableService = tableService;
    }

    public ObservableList<TableDTO> getTables() { return tables; }
    public ObjectProperty<TableDTO> selectedTableProperty() { return selectedTable; }
    public StringProperty errorMessageProperty() { return errorMessage; }

    private <T> void runAsync(Callable<T> action, Consumer<T> onSuccess) {
        errorMessage.set("");

        Task<T> task = new Task<>() {
            @Override
            protected T call() throws Exception {
                return action.call();
            }
        };

        task.setOnSucceeded(event -> onSuccess.accept(task.getValue()));
        task.setOnFailed(event -> {
            Throwable ex = task.getException();
            errorMessage.set("Erreur : " + (ex != null ? ex.getMessage() : "inconnue"));
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void loadAll() {
        runAsync(tableService::getAll, tables::setAll);
    }

    public void search(String keyword) {
        runAsync(() -> tableService.search(keyword), tables::setAll);
    }

    public void save(TableDTO tableToSave) {
        if (tableToSave == null) return;

        Callable<TableDTO> action = (tableToSave.getIdtable() != null && !tableToSave.getIdtable().isBlank())
                ? () -> tableService.update(tableToSave)
                : () -> tableService.create(tableToSave);

        runAsync(action, saved -> {
            loadAll();
            selectedTable.set(null);
        });
    }

    public void delete(String id) {
        if (id == null || id.isBlank()) return;

        runAsync(() -> {
            tableService.delete(id);
            return null;
        }, ignored -> {
            loadAll();
            selectedTable.set(null);
        });
    }
}