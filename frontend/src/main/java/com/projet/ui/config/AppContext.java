package com.projet.ui.config;

import com.projet.ui.services.ApiClient;
import com.projet.ui.services.MenuService;
import com.projet.ui.services.TableService;
// import com.projet.ui.services.CommandeService;
// import com.projet.ui.services.ReserverService;

public class AppContext {

    private static final ApiClient apiClient = new ApiClient();

    private static final MenuService menuService = new MenuService(apiClient);
    private static final TableService tableService = new TableService(apiClient);
    // private static final CommandeService commandeService = new CommandeService(apiClient);
    // private static final ReserverService reserverService = new ReserverService(apiClient);

    private AppContext() {} // pas d'instance, uniquement statique

    public static MenuService getMenuService() { return menuService; }
    public static TableService getTableService() { return tableService; }
    // public static CommandeService getCommandeService() { return commandeService; }
    // public static ReserverService getReserverService() { return reserverService; }
}