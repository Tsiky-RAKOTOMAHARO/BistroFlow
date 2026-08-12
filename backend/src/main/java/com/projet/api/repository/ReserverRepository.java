package com.projet.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projet.api.model.Reserver;

public interface ReserverRepository extends JpaRepository<Reserver, String> {

    List<Reserver> findByNomcliContainingIgnoreCase(String keyword);

    @Query("SELECT r FROM Reserver r WHERE r.restaurantTable.idtable = :idtable AND r.date_reserve <= :date")
    List<Reserver> findByTableAndDateLessThanEqual(@Param("idtable") String idtable, @Param("date") LocalDateTime date);
}