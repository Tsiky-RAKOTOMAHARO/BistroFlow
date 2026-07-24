package com.projet.api.mapper;

import com.projet.api.model.RestaurantTable;
import com.projet.common.dto.TableDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantTableMapper {

    public TableDTO toDTO(RestaurantTable entity) {
        if (entity == null) {
            return null;
        }

        TableDTO dto = new TableDTO();
        dto.setIdtable(entity.getIdtable());
        dto.setActif(entity.isActif());
        dto.setDesignation(entity.getDesignation());
        dto.setOccupation(entity.getOccupation());
        return dto;
    }

    public RestaurantTable toEntity(TableDTO dto) {
        if (dto == null) {
            return null;
        }

        RestaurantTable entity = new RestaurantTable();
        entity.setIdtable(dto.getIdtable());
        entity.setActif(dto.isActif());
        entity.setDesignation(dto.getDesignation());
        entity.setOccupation(dto.getOccupation());
        return entity;
    }

    public List<TableDTO> toDTOList(List<RestaurantTable> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}