package com.projet.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projet.api.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, String>{

    boolean existsByRestaurantTableIdtableAndPayeFalse(String idtable);

    List<Commande> findByNomcliContainingIgnoreCase(String keyword);
} 
       













                            
