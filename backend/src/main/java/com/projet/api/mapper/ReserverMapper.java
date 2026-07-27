package com.projet.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projet.api.model.Reserver;
import com.projet.common.dto.ReserverDTO;

@Component
public class ReserverMapper {

    
    // entite en dto
    public ReserverDTO toDTO(Reserver entity){
        if(entity == null){
            return null;
        }

        ReserverDTO dto = new ReserverDTO();
        dto.setIdreserv(entity.getIdreserv());
        dto.setNomcli(entity.getNomcli());
        dto.setDate_de_reserv(entity.getDate_de_reserv());
        dto.setDate_reserve(entity.getDate_reserve());

        if (entity.getRestaurantTable() != null) {
            dto.setIdtable(entity.getRestaurantTable().getIdtable());
        }

        return dto;
    }

    // dto en entite
    public Reserver toEntity(ReserverDTO dto){
        if(dto == null){
            return null;
        }

        Reserver entity = new Reserver();
        entity.setIdreserv(dto.getIdreserv());
        entity.setNomcli(dto.getNomcli());
        entity.setDate_de_reserv(dto.getDate_de_reserv());
        entity.setDate_reserve(dto.getDate_reserve());


        return entity;

    }

    // to list ??
    public List<ReserverDTO> toDTOList(List<Reserver> entities){

        return entities.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }
}