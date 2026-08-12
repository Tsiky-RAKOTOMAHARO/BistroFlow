package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet.api.service.ReserverService;
import com.projet.common.dto.ReserverDTO;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "*")
public class ReserverController {

    private final ReserverService reserverService;

    public ReserverController(ReserverService reserverService) {
        this.reserverService = reserverService;
    }

    @GetMapping
    public ResponseEntity<List<ReserverDTO>> getAllReservations(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(reserverService.searchByNomcli(search));
        }
        return ResponseEntity.ok(reserverService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReserverDTO> getReservationById(@PathVariable String id) {
        return ResponseEntity.ok(reserverService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ReserverDTO> createReservation(@RequestBody ReserverDTO reserverDTO) {
        ReserverDTO created = reserverService.createReserver(reserverDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReserverDTO> updateReservation(@PathVariable String id, @RequestBody ReserverDTO reserverDTO) {
        reserverDTO.setIdreserv(id);
        ReserverDTO updated = reserverService.updateReserver(reserverDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        reserverService.deleteReserver(id);
        return ResponseEntity.noContent().build();
    }
}