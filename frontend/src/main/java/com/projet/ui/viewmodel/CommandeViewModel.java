package com.projet.ui.viewmodel;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import com.projet.common.dto.CommandeDTO;
import com.projet.ui.services.CommandeService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class CommandeViewModel {

    private final CommandeService commandeService;

    private final ObservableList<CommandeDTO> commandes = FXCollections.observableArrayList();
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public CommandeViewModel(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    public ObservableList<CommandeDTO> getCommandes() { return commandes; }
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
        runAsync(commandeService::getAll, commandes::setAll);
    }

    public void search(String keyword) {
        runAsync(() -> commandeService.search(keyword), commandes::setAll);
    }

    public void save(CommandeDTO toSave) {
        if (toSave == null) return;

        Callable<CommandeDTO> action = (toSave.getIdcom() != null && !toSave.getIdcom().isBlank())
                ? () -> commandeService.update(toSave)
                : () -> commandeService.create(toSave);

        runAsync(action, saved -> loadAll());
    }

    public void delete(String id) {
        if (id == null || id.isBlank()) return;

        runAsync(() -> {
            commandeService.delete(id);
            return null;
        }, ignored -> loadAll());
    }
}