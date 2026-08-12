package com.projet.api.repository;

import com.projet.api.model.RestaurantTable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, String> {

    List<RestaurantTable> findByOccupation(Boolean occupation);
    
    List<RestaurantTable> findByActif(boolean actif);

    List<RestaurantTable> findByDesignationContainingIgnoreCase(String keyword);

}