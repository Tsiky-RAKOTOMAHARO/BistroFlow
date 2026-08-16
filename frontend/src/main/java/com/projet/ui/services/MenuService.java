package com.projet.ui.services;

import com.projet.common.dto.MenuDTO;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MenuService {

    private final ApiClient apiClient;
    private static final String ENDPOINT = "/menus";

    public MenuService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<MenuDTO> getAll() throws Exception {
        return apiClient.getList(ENDPOINT, MenuDTO.class);
    }

    public MenuDTO getById(String id) throws Exception {
        return apiClient.get(ENDPOINT + "/" + id, MenuDTO.class);
    }

    public MenuDTO create(MenuDTO menu) throws Exception {
        return apiClient.post(ENDPOINT, menu, MenuDTO.class);
    }

    public MenuDTO update(MenuDTO menu) throws Exception {
        return apiClient.put(ENDPOINT + "/" + menu.getIdplat(), menu, MenuDTO.class);
    }

    public void delete(String id) throws Exception {
        apiClient.delete(ENDPOINT + "/" + id);
    }

    public List<MenuDTO> search(String keyWord) throws Exception {
        if (keyWord == null || keyWord.isBlank()) {
            return getAll();
        }
    return apiClient.getList(ENDPOINT + "?search=" + URLEncoder.encode(keyWord, StandardCharsets.UTF_8), MenuDTO.class);
    }
}