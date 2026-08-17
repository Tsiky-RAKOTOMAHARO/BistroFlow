package com.projet.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projet.api.model.Commande;
import com.projet.common.dto.CommandeDTO;

@Component
public class CommandeMapper {

    private final LigneCommandeMapper ligneCommandeMapper;

    public CommandeMapper(LigneCommandeMapper ligneCommandeMapper) {
        this.ligneCommandeMapper = ligneCommandeMapper;
    }

    public CommandeDTO toDTO(Commande entity){
        if (entity == null) return null;

        CommandeDTO dto = new CommandeDTO();
        dto.setIdcom(entity.getIdcom());
        dto.setNomcli(entity.getNomcli());
        dto.setTypecom(entity.getTypecom());
        dto.setDatecom(entity.getDatecom());
        dto.setPaye(entity.isPaye());

        if(entity.getRestaurantTable() != null){
            dto.setIdtable(entity.getRestaurantTable().getIdtable());
        }
        dto.setLignes(ligneCommandeMapper.toDTOList(entity.getLignes()));

        return dto;
    }

    public Commande toEntity(CommandeDTO dto){
        if (dto == null) return null;

        Commande entity = new Commande();
        entity.setIdcom(dto.getIdcom());
        entity.setNomcli(dto.getNomcli());
        entity.setTypecom(dto.getTypecom());
        entity.setDatecom(dto.getDatecom());
        entity.setPaye(dto.isPaye());

        return entity;
    }

    public List<CommandeDTO> toDTOList(List<Commande> entities){
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}