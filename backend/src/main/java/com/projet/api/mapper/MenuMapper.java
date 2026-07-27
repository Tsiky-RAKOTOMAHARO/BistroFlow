package com.projet.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projet.api.model.Menu;
import com.projet.common.dto.MenuDTO;

@Component
public class MenuMapper {

    public MenuDTO toDTO(Menu entity){
        if (entity == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setIdplat(entity.getIdplat());
        dto.setNomplat(entity.getNomplat());
        dto.setActif(entity.isActif());
        dto.setPu(entity.getPu());
        

        return dto;
    }

    public Menu toEntity(MenuDTO dto){
        if (dto == null) {
            return null;
        }

        Menu entity = new Menu();
        entity.setIdplat(dto.getIdplat());
        entity.setNomplat(dto.getNomplat());
        entity.setActif(dto.isActif());
        entity.setPu(dto.getPu());

        return entity;
    }

    public List<MenuDTO> toDTOList(List<Menu> entities){
        return entities.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }
    
}