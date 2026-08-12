package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet.api.service.MenuService;
import com.projet.common.dto.MenuDTO;

@RestController
@RequestMapping("/api/v1/menus")
@CrossOrigin(origins = "*") 
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(menuService.searchMenu(search));
        }
        return ResponseEntity.ok(menuService.getAll());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable String id) {
        return ResponseEntity.ok(menuService.getById(id));
    }

    
    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        MenuDTO created = menuService.createMenu(menuDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable String id, @RequestBody MenuDTO menuDTO) {
        menuDTO.setIdplat(id); 
        MenuDTO updated = menuService.updateMenu(menuDTO);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}