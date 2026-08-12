package com.projet.api.service;

import java.util.List;
import com.projet.common.dto.LigneCommandeDTO;

public interface LigneCommandeService {

    List<LigneCommandeDTO> getAll();

    List<LigneCommandeDTO> getByCommandeId(String idcom);

    LigneCommandeDTO getById(String idligne);

    LigneCommandeDTO saveLigneCommande(LigneCommandeDTO dto);

    void deleteLigneCommande(String idligne);
}