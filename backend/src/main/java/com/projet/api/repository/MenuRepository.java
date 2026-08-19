package com.projet.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, String>{

    List<Menu> findByNomplatContainingIgnoreCase(String keyword);    
}