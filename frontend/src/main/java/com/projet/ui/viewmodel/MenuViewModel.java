package com.projet.ui.viewmodel;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import com.projet.common.dto.MenuDTO;
import com.projet.ui.services.MenuService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class MenuViewModel {

    private final MenuService menuService;

    private final ObservableList<MenuDTO> menus = FXCollections.observableArrayList();
    private final ObjectProperty<MenuDTO> selectedMenu = new SimpleObjectProperty<>();
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public MenuViewModel(MenuService menuService) {
        this.menuService = menuService;
    }

    public ObservableList<MenuDTO> getMenus() { return menus; }
    public ObjectProperty<MenuDTO> selectedMenuProperty() { return selectedMenu; }
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
        runAsync(menuService::getAll, menus::setAll);
    }

    public void save(MenuDTO menuToSave) {
        if (menuToSave == null) return;

        Callable<MenuDTO> action = (menuToSave.getIdplat() != null && !menuToSave.getIdplat().isBlank())
                ? () -> menuService.update(menuToSave)
                : () -> menuService.create(menuToSave);

        runAsync(action, saved -> {
            loadAll();
            selectedMenu.set(null);
        });
    }

    public void delete(String id) {
        if (id == null || id.isBlank()) return;

        runAsync(() -> {
            menuService.delete(id);
            return null; 
        }, ignored -> {
            loadAll();
            selectedMenu.set(null);
        });
    }
    
    public void search(String keyWord) {
        runAsync(() -> menuService.search(keyWord), menus::setAll);
    }
}