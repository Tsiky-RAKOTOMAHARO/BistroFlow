package com.projet.api.service;

import java.util.List;

import com.projet.common.dto.CommandeDTO;

public interface CommandeService {

     List<CommandeDTO> getAll();

     CommandeDTO getById(String id);

     CommandeDTO createCommande(CommandeDTO commande);
     
     CommandeDTO updateCommande(CommandeDTO commande);

     void deleteCommande(String id);

     List<CommandeDTO> searchCommande(String keyword);
}