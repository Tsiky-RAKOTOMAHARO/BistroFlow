package com.projet.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.api.mapper.LigneCommandeMapper;
import com.projet.api.model.Commande;
import com.projet.api.model.LigneCommande;
import com.projet.api.model.Menu;
import com.projet.api.repository.CommandeRepository;
import com.projet.api.repository.LigneCommandeRepository;
import com.projet.api.repository.MenuRepository;
import com.projet.api.service.LigneCommandeService;
import com.projet.common.dto.LigneCommandeDTO;

@Service
public class LigneCommandeServiceImpl implements LigneCommandeService {

    private final LigneCommandeRepository ligneCommandeRepository;
    private final CommandeRepository commandeRepository;
    private final MenuRepository menuRepository;
    private final LigneCommandeMapper ligneCommandeMapper;

    public LigneCommandeServiceImpl(
            LigneCommandeRepository ligneCommandeRepository,
            CommandeRepository commandeRepository,
            MenuRepository menuRepository,
            LigneCommandeMapper ligneCommandeMapper) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.commandeRepository = commandeRepository;
        this.menuRepository = menuRepository;
        this.ligneCommandeMapper = ligneCommandeMapper;
    }

    @Override
    public List<LigneCommandeDTO> getAll() {
        return ligneCommandeRepository.findAll().stream()
                .map(ligneCommandeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeDTO> getByCommandeId(String idcom) {
        return ligneCommandeRepository.findByCommandeIdcom(idcom).stream()
                .map(ligneCommandeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LigneCommandeDTO getById(String idligne) {
        LigneCommande ligne = ligneCommandeRepository.findById(idligne)
                .orElseThrow(() -> new RuntimeException("Ligne de commande introuvable : " + idligne));
        return ligneCommandeMapper.toDTO(ligne);
    }

    @Override
    @Transactional
    public LigneCommandeDTO saveLigneCommande(LigneCommandeDTO dto) {
        LigneCommande entity = ligneCommandeMapper.toEntity(dto);

        if (dto.getIdcom() != null) {
            Commande commande = commandeRepository.findById(dto.getIdcom())
                    .orElseThrow(() -> new RuntimeException("Commande introuvable : " + dto.getIdcom()));
            entity.setCommande(commande);
        }

        if (dto.getIdplat() != null) {
            Menu menu = menuRepository.findById(dto.getIdplat())
                    .orElseThrow(() -> new RuntimeException("Plat introuvable : " + dto.getIdplat()));
            entity.setMenu(menu);
        }

        if (entity.getIdligne() == null || entity.getIdligne().isBlank()) {
            long count = ligneCommandeRepository.count();
            entity.setIdligne("LIG-" + (count + 1));
        }

        LigneCommande saved = ligneCommandeRepository.save(entity);
        return ligneCommandeMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void deleteLigneCommande(String idligne) {
        if (!ligneCommandeRepository.existsById(idligne)) {
            throw new RuntimeException("Impossible de supprimer : ligne introuvable (" + idligne + ")");
        }
        ligneCommandeRepository.deleteById(idligne);
    }
}