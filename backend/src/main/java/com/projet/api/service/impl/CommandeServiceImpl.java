package com.projet.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.api.mapper.CommandeMapper;
import com.projet.api.mapper.LigneCommandeMapper;
import com.projet.api.model.Commande;
import com.projet.api.model.LigneCommande;
import com.projet.api.model.Menu;
import com.projet.api.model.RestaurantTable;
import com.projet.api.repository.CommandeRepository;
import com.projet.api.repository.MenuRepository;
import com.projet.api.repository.RestaurantTableRepository;
import com.projet.api.service.CommandeService;
import com.projet.common.dto.CommandeDTO;
import com.projet.common.dto.LigneCommandeDTO;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuRepository menuRepository;
    private final CommandeMapper commandeMapper;
    private final LigneCommandeMapper ligneCommandeMapper;

    public CommandeServiceImpl(
            CommandeRepository commandeRepository,
            RestaurantTableRepository tableRepository,
            MenuRepository menuRepository,
            CommandeMapper commandeMapper,
            LigneCommandeMapper ligneCommandeMapper) {
        this.commandeRepository = commandeRepository;
        this.tableRepository = tableRepository;
        this.menuRepository = menuRepository;
        this.commandeMapper = commandeMapper;
        this.ligneCommandeMapper = ligneCommandeMapper;
    }

    @Override
    public List<CommandeDTO> getAll() {
        return commandeRepository.findAll().stream()
                .map(commandeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeDTO getById(String id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + id));

        return commandeMapper.toDTO(commande);
    }

    @Override
    @Transactional 
    public CommandeDTO createCommande(CommandeDTO commandeDTO) {
        // 1. Génération de l'ID
        long totalCommandes = commandeRepository.count();
        String newId = "CMD-" + (totalCommandes + 1);

        Commande commandeEntity = commandeMapper.toEntity(commandeDTO);
        commandeEntity.setIdcom(newId);

        if (commandeEntity.getDatecom() == null) {
            commandeEntity.setDatecom(LocalDateTime.now());
        }
        if (commandeEntity.isPaye() == null) {
            commandeEntity.setPaye(false);
        }

        if (commandeDTO.getIdtable() != null && !commandeDTO.getIdtable().isBlank()) {
            RestaurantTable table = tableRepository.findById(commandeDTO.getIdtable())
                    .orElseThrow(() -> new RuntimeException("Table introuvable : " + commandeDTO.getIdtable()));
            commandeEntity.setRestaurantTable(table);
        }

        if (commandeDTO.getLignes() != null && !commandeDTO.getLignes().isEmpty()) {
            for (LigneCommandeDTO ligneDTO : commandeDTO.getLignes()) {
                LigneCommande ligneEntity = ligneCommandeMapper.toEntity(ligneDTO);

                Menu menu = menuRepository.findById(ligneDTO.getIdplat())
                        .orElseThrow(() -> new RuntimeException("Plat introuvable : " + ligneDTO.getIdplat()));
                ligneEntity.setMenu(menu);

                ligneEntity.setCommande(commandeEntity);

                commandeEntity.getLignes().add(ligneEntity);
            }
        }

        Commande savedCommande = commandeRepository.save(commandeEntity);
        return commandeMapper.toDTO(savedCommande);
    }

    @Override
    @Transactional
    public CommandeDTO updateCommande(CommandeDTO commandeDTO) {
        Commande existing = commandeRepository.findById(commandeDTO.getIdcom())
                .orElseThrow(() -> new RuntimeException("Commande introuvable pour modification : " + commandeDTO.getIdcom()));

        existing.setNomcli(commandeDTO.getNomcli());
        existing.setTypecom(commandeDTO.getTypecom());

        if (commandeDTO.isPaye() != null) {
            existing.setPaye(commandeDTO.isPaye());
        }
        if (commandeDTO.getDatecom() != null) {
            existing.setDatecom(commandeDTO.getDatecom());
        }

        if (commandeDTO.getIdtable() != null && !commandeDTO.getIdtable().isBlank()) {
            RestaurantTable table = tableRepository.findById(commandeDTO.getIdtable())
                    .orElseThrow(() -> new RuntimeException("Table introuvable : " + commandeDTO.getIdtable()));
            existing.setRestaurantTable(table);
        } else {
            existing.setRestaurantTable(null);
        }

        existing.getLignes().clear();
        if (commandeDTO.getLignes() != null) {
            for (LigneCommandeDTO ligneDTO : commandeDTO.getLignes()) {
                LigneCommande ligneEntity = ligneCommandeMapper.toEntity(ligneDTO);

                Menu menu = menuRepository.findById(ligneDTO.getIdplat())
                        .orElseThrow(() -> new RuntimeException("Plat introuvable : " + ligneDTO.getIdplat()));
                ligneEntity.setMenu(menu);
                ligneEntity.setCommande(existing);

                existing.getLignes().add(ligneEntity);
            }
        }

        Commande updatedCommande = commandeRepository.save(existing);
        return commandeMapper.toDTO(updatedCommande);
    }

    @Override
    public void deleteCommande(String id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + id));

        commandeRepository.delete(commande);
    }

    @Override
    public List<CommandeDTO> searchCommande(String keyword) {
        List<Commande> resultats = commandeRepository.findByNomcliContainingIgnoreCase(keyword);

        return resultats.stream()
                .map(commandeMapper::toDTO)
                .collect(Collectors.toList());
    }
}