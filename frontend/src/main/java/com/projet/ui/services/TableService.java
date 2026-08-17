package com.projet.ui.services;

import com.projet.common.dto.TableDTO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TableService {

    private final ApiClient apiClient;
    private static final String ENDPOINT = "/tables";

    public TableService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<TableDTO> getAll() throws Exception {
        return apiClient.getList(ENDPOINT, TableDTO.class);
    }

    public TableDTO getById(String id) throws Exception {
        return apiClient.get(ENDPOINT + "/" + id, TableDTO.class);
    }

    public TableDTO create(TableDTO table) throws Exception {
        return apiClient.post(ENDPOINT, table, TableDTO.class);
    }

    public TableDTO update(TableDTO table) throws Exception {
        return apiClient.put(ENDPOINT + "/" + table.getIdtable(), table, TableDTO.class);
    }

    public void delete(String id) throws Exception {
        apiClient.delete(ENDPOINT + "/" + id);
    }

    public List<TableDTO> search(String keyword) throws Exception {
        if (keyword == null || keyword.isBlank()) {
            return getAll();
        }
        String encoded = URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8);
        return apiClient.getList(ENDPOINT + "?keyword=" + encoded, TableDTO.class);
    }
}