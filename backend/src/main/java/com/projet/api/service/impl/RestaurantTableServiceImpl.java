package com.projet.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projet.api.mapper.RestaurantTableMapper;
import com.projet.api.model.RestaurantTable;
import com.projet.api.repository.RestaurantTableRepository;
import com.projet.api.service.RestaurantTableService;
import com.projet.common.dto.TableDTO;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService{

    public final RestaurantTableRepository tableRepository;
    public final RestaurantTableMapper tableMapper;

    public RestaurantTableServiceImpl(RestaurantTableRepository tableRepository, RestaurantTableMapper tableMapper){
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    @Override
    public List<TableDTO> getAll() {
        return tableRepository.findAll().stream()
            .map(tableMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TableDTO getById(String id) {
        RestaurantTable table = tableRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Table introuvable " + id));

        return tableMapper.toDTO(table);
    }

    @Override
    public TableDTO createTable(TableDTO table) {

        long totalTables = tableRepository.count();
        String newId = "TO" + (totalTables + 1);

        RestaurantTable tableEntity = tableMapper.toEntity(table);

        tableEntity.setIdtable(newId);
        tableEntity.setDesignation(table.getDesignation());
        tableEntity.setOccupation(table.getOccupation());
        tableEntity.setActif(true);

        RestaurantTable savedTable = tableRepository.save(tableEntity);

        return tableMapper.toDTO(savedTable);
    }

    @Override
    public TableDTO updateTable(TableDTO table) {
        
        RestaurantTable existingtable = tableRepository.findById(table.getIdtable())
            .orElseThrow(() -> new RuntimeException("Table introuvable pour la modification : " +table.getIdtable()));
        
        existingtable.setDesignation(table.getDesignation());
        existingtable.setOccupation(table.getOccupation());
        existingtable.setActif(table.isActif());

        RestaurantTable updatedTable = tableRepository.save(existingtable);

        return tableMapper.toDTO(updatedTable);

    }

    @Override
    public void deleteTable(String id) {
        
        RestaurantTable table = tableRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Table introuvable " + id));

        table.setActif(false);
        
        tableRepository.save(table);
    }

    @Override
    public List<TableDTO> searchTable(String keyword) {
        List<RestaurantTable> resultats = tableRepository.findByDesignationContainingIgnoreCase(keyword);

        return resultats.stream()
            .map(tableMapper::toDTO)
            .collect(Collectors.toList());
    }

   @Override
    public List<TableDTO> getByStatut(String statut) {
    Boolean isOccupied = null;

    if ("occupee".equalsIgnoreCase(statut) || "true".equalsIgnoreCase(statut)) {
        isOccupied = true;
    } else if ("libre".equalsIgnoreCase(statut) || "false".equalsIgnoreCase(statut)) {
        isOccupied = false;
    }

    List<RestaurantTable> tables = (isOccupied != null)
            ? tableRepository.findByOccupation(isOccupied)
            : tableRepository.findAll();

    return tables.stream()
            .map(tableMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    
}