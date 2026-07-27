package com.projet.api.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projet.api.model.LigneCommande;
import com.projet.common.dto.LigneCommandeDTO;

@Component
public class LigneCommandeMapper {
    
    public LigneCommandeDTO toDTO(LigneCommande entity) {
        if (entity == null) {
            return null;
        }

        LigneCommandeDTO dto = new LigneCommandeDTO();
        dto.setIdligne(entity.getIdligne());
        dto.setQuantite(entity.getQuantite());

        if (entity.getCommande() != null) {
            dto.setIdcom(entity.getCommande().getIdcom()); 
        }
        if (entity.getMenu() != null) {
            dto.setIdplat(entity.getMenu().getIdplat()); 
        }
        return dto;
    }

    public LigneCommande toEntity(LigneCommandeDTO dto) {
        if (dto == null) {
            return null;
        }

        LigneCommande entity = new LigneCommande();
        entity.setIdligne(dto.getIdligne());
        entity.setQuantite(dto.getQuantite());

        

        return entity;
    }

    public List<LigneCommandeDTO> toDTOList(List<LigneCommande> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }
}