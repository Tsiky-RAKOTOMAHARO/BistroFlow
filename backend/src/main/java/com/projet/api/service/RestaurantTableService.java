package com.projet.api.service;

import com.projet.common.dto.TableDTO;
import java.util.List;

public interface RestaurantTableService {

     List<TableDTO> getAll();

     TableDTO getById(String id);
     
     TableDTO createTable(TableDTO table);

     TableDTO updateTable(TableDTO table);

     void deleteTable(String id);

     List<TableDTO> searchTable(String keyword);

     Object getByStatut(String statut);
}