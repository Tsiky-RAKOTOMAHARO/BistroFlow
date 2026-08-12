package com.projet.api.service;

import java.util.List;

import com.projet.common.dto.MenuDTO;



public interface MenuService {

    List<MenuDTO> getAll();

    MenuDTO getById(String id);

    MenuDTO createMenu(MenuDTO menu);

    MenuDTO updateMenu(MenuDTO menu);

    void deleteMenu(String id); 

    List<MenuDTO> searchMenu(String keyword);
}