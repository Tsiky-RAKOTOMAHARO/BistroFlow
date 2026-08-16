package com.projet.api.service.impl;

import com.projet.api.repository.MenuRepository;
import com.projet.api.service.MenuService;
import com.projet.common.dto.MenuDTO;
import com.projet.api.mapper.MenuMapper;
import com.projet.api.model.Menu;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service 
public class MenuServiceImpl implements MenuService {

    
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    
    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public List<MenuDTO> getAll() {
        return menuRepository.findAll().stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDTO getById(String id) {
        
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu introuvable avec l'ID : " + id)); 
        
        return menuMapper.toDTO(menu);
    }

    @Override
    public MenuDTO createMenu(MenuDTO menuDto) {
      
        long totalMenus = menuRepository.count();
        String newId = "PO" + (totalMenus + 1);

       
        Menu menuEntity = menuMapper.toEntity(menuDto);


        menuEntity.setIdplat(newId);
        menuEntity.setActif(true);

        
        Menu savedMenu = menuRepository.save(menuEntity);
        return menuMapper.toDTO(savedMenu);
    }

    @Override
    public MenuDTO updateMenu(MenuDTO menu){

        //Menu existant
        @SuppressWarnings("null")
        Menu existingMenu = menuRepository.findById(menu.getIdplat())
        .orElseThrow(() -> new RuntimeException("Menu introuvable pour la mise a jour : " + menu.getIdplat()));


        existingMenu.setNomplat(menu.getNomplat());
        existingMenu.setPu(menu.getPu());
        existingMenu.setActif(menu.isActif());

        Menu updateMenu = menuRepository.save(existingMenu);

        return menuMapper.toDTO(updateMenu);

    }

    @Override
    public void deleteMenu(String id) {

        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu introuvable pour la suppression : " + id));

        menu.setActif(false);

        menuRepository.save(menu);
        
    }

    @Override
    public List<MenuDTO> searchMenu(String keyword) {
    List<Menu> resultats = menuRepository.findByNomplatContainingIgnoreCase(keyword);

        return resultats.stream()
            .map(menuMapper::toDTO)
            .collect(Collectors.toList());
    }
}