package com.projet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.model.LigneCommande;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, String>{

    
} 
