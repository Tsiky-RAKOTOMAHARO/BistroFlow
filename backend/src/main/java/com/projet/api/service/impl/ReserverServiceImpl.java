package com.projet.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.api.mapper.ReserverMapper;
import com.projet.api.model.Reserver;
import com.projet.api.model.RestaurantTable;
import com.projet.api.repository.ReserverRepository;
import com.projet.api.repository.RestaurantTableRepository;
import com.projet.api.service.ReserverService;
import com.projet.common.dto.ReserverDTO;

@Service
public class ReserverServiceImpl implements ReserverService {

    private final ReserverRepository reserverRepository;
    private final RestaurantTableRepository tableRepository;
    private final ReserverMapper reserverMapper;

    public ReserverServiceImpl(
            ReserverRepository reserverRepository,
            RestaurantTableRepository tableRepository,
            ReserverMapper reserverMapper) {
        this.reserverRepository = reserverRepository;
        this.tableRepository = tableRepository;
        this.reserverMapper = reserverMapper;
    }

    @Override
    public List<ReserverDTO> getAll() {
        return reserverRepository.findAll().stream()
                .map(reserverMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReserverDTO getById(String id) {
        Reserver reserver = reserverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable avec l'ID : " + id));
        return reserverMapper.toDTO(reserver);
    }

    @Override
    @Transactional
    public ReserverDTO createReserver(ReserverDTO reserverDTO) {
        String newId = reserverDTO.getIdreserv();
        if (newId == null || newId.isBlank()) {
            long totalReservations = reserverRepository.count();
            newId = "RES-O" + (totalReservations + 1);
        }

        Reserver entity = reserverMapper.toEntity(reserverDTO);
        entity.setIdreserv(newId);

        if (entity.getDate_de_reserv() == null) {
            entity.setDate_de_reserv(LocalDateTime.now());
        }

        if (reserverDTO.getIdtable() != null && !reserverDTO.getIdtable().isBlank()) {
            RestaurantTable table = tableRepository.findById(reserverDTO.getIdtable())
                    .orElseThrow(() -> new RuntimeException("Table introuvable : " + reserverDTO.getIdtable()));
            entity.setRestaurantTable(table);
        }

        Reserver savedReserver = reserverRepository.save(entity);
        return reserverMapper.toDTO(savedReserver);
    }

    @Override
    @Transactional
    public ReserverDTO updateReserver(ReserverDTO reserverDTO) {
        Reserver existing = reserverRepository.findById(reserverDTO.getIdreserv())
                .orElseThrow(() -> new RuntimeException("Réservation introuvable pour modification : " + reserverDTO.getIdreserv()));

        existing.setNomcli(reserverDTO.getNomcli());

        if (reserverDTO.getDate_de_reserv() != null) {
            existing.setDate_de_reserv(reserverDTO.getDate_de_reserv());
        }
        if (reserverDTO.getDate_reserve() != null) {
            existing.setDate_reserve(reserverDTO.getDate_reserve());
        }

        if (reserverDTO.getIdtable() != null && !reserverDTO.getIdtable().isBlank()) {
            RestaurantTable table = tableRepository.findById(reserverDTO.getIdtable())
                    .orElseThrow(() -> new RuntimeException("Table introuvable : " + reserverDTO.getIdtable()));
            existing.setRestaurantTable(table);
        } else {
            existing.setRestaurantTable(null);
        }

        Reserver updated = reserverRepository.save(existing);
        return reserverMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteReserver(String id) {
        if (!reserverRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : réservation introuvable (" + id + ")");
        }
        reserverRepository.deleteById(id);
    }

    @Override
    public List<ReserverDTO> searchByNomcli(String keyword) {
        List<Reserver> resultats = reserverRepository.findByNomcliContainingIgnoreCase(keyword);
        
        return   resultats.stream()
                .map(reserverMapper::toDTO)
                .collect(Collectors.toList());
    }
}