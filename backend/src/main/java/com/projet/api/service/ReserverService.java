package com.projet.api.service;

import java.util.List;
import com.projet.common.dto.ReserverDTO;

public interface ReserverService {

    List<ReserverDTO> getAll();

    ReserverDTO getById(String id);

    ReserverDTO createReserver(ReserverDTO reserverDTO);

    ReserverDTO updateReserver(ReserverDTO reserverDTO);

    void deleteReserver(String id);

    List<ReserverDTO> searchByNomcli(String keyword);
}