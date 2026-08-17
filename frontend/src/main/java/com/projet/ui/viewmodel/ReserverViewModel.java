package com.projet.ui.viewmodel;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import com.projet.common.dto.ReserverDTO;
import com.projet.ui.services.ReserverService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ReserverViewModel {

    private final ReserverService reserverService;

    private final ObservableList<ReserverDTO> reservations = FXCollections.observableArrayList();
    private final ObjectProperty<ReserverDTO> selectedReserver = new SimpleObjectProperty<>();
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public ReserverViewModel(ReserverService reserverService) {
        this.reserverService = reserverService;
    }

    public ObservableList<ReserverDTO> getReservations() { return reservations; }
    public ObjectProperty<ReserverDTO> selectedReserverProperty() { return selectedReserver; }
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
        runAsync(reserverService::getAll, reservations::setAll);
    }

    public void search(String keyword) {
        runAsync(() -> reserverService.search(keyword), reservations::setAll);
    }

    public void save(ReserverDTO toSave) {
        if (toSave == null) return;

        Callable<ReserverDTO> action = (toSave.getIdreserv() != null && !toSave.getIdreserv().isBlank())
                ? () -> reserverService.update(toSave)
                : () -> reserverService.create(toSave);

        runAsync(action, saved -> {
            loadAll();
            selectedReserver.set(null);
        });
    }

    public void delete(String id) {
        if (id == null || id.isBlank()) return;

        runAsync(() -> {
            reserverService.delete(id);
            return null;
        }, ignored -> {
            loadAll();
            selectedReserver.set(null);
        });
    }
}