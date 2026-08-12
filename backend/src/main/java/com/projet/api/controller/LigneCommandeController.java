package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.api.service.LigneCommandeService;
import com.projet.common.dto.LigneCommandeDTO;

@RestController
@RequestMapping("/api/v1/lignes-commande")
@CrossOrigin(origins = "*")
public class LigneCommandeController {

    private final LigneCommandeService ligneCommandeService;

    public LigneCommandeController(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }

    @GetMapping
    public ResponseEntity<List<LigneCommandeDTO>> getAllLignes() {
        return ResponseEntity.ok(ligneCommandeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommandeDTO> getLigneById(@PathVariable String id) {
        return ResponseEntity.ok(ligneCommandeService.getById(id));
    }

    @GetMapping("/commande/{idcom}")
    public ResponseEntity<List<LigneCommandeDTO>> getLignesByCommandeId(@PathVariable String idcom) {
        return ResponseEntity.ok(ligneCommandeService.getByCommandeId(idcom));
    }

    @PostMapping
    public ResponseEntity<LigneCommandeDTO> createLigneCommande(@RequestBody LigneCommandeDTO dto) {
        LigneCommandeDTO created = ligneCommandeService.saveLigneCommande(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable String id) {
        ligneCommandeService.deleteLigneCommande(id);
        return ResponseEntity.noContent().build();
    }
}