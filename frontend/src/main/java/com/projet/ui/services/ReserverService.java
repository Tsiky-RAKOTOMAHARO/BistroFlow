package com.projet.ui.services;

import com.projet.common.dto.ReserverDTO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReserverService {

    private final ApiClient apiClient;
    private static final String ENDPOINT = "/reservations";

    public ReserverService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<ReserverDTO> getAll() throws Exception {
        return apiClient.getList(ENDPOINT, ReserverDTO.class);
    }

    public ReserverDTO getById(String id) throws Exception {
        return apiClient.get(ENDPOINT + "/" + id, ReserverDTO.class);
    }

    public ReserverDTO create(ReserverDTO reserver) throws Exception {
        return apiClient.post(ENDPOINT, reserver, ReserverDTO.class);
    }

    public ReserverDTO update(ReserverDTO reserver) throws Exception {
        return apiClient.put(ENDPOINT + "/" + reserver.getIdreserv(), reserver, ReserverDTO.class);
    }

    public void delete(String id) throws Exception {
        apiClient.delete(ENDPOINT + "/" + id);
    }

    public List<ReserverDTO> search(String keyword) throws Exception {
        if (keyword == null || keyword.isBlank()) {
            return getAll();
        }
        return apiClient.getList(ENDPOINT + "?search=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8), ReserverDTO.class);
    }
}