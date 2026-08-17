package com.projet.ui.services;

import com.projet.common.dto.CommandeDTO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CommandeService {

    private final ApiClient apiClient;
    private static final String ENDPOINT = "/commandes";

    public CommandeService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<CommandeDTO> getAll() throws Exception {
        return apiClient.getList(ENDPOINT, CommandeDTO.class);
    }

    public CommandeDTO create(CommandeDTO commande) throws Exception {
        return apiClient.post(ENDPOINT, commande, CommandeDTO.class);
    }

    public CommandeDTO update(CommandeDTO commande) throws Exception {
        return apiClient.put(ENDPOINT + "/" + commande.getIdcom(), commande, CommandeDTO.class);
    }

    public void delete(String id) throws Exception {
        apiClient.delete(ENDPOINT + "/" + id);
    }

    public List<CommandeDTO> search(String keyword) throws Exception {
        if (keyword == null || keyword.isBlank()) return getAll();
        return apiClient.getList(ENDPOINT + "?search=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8), CommandeDTO.class);
    }
}