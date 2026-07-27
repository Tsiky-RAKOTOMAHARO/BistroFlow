package com.projet.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TABLE_")
public class RestaurantTable {

    @Id
    @Column(name = "idtable", length = 25)
    private String idtable;

    @Column(name = "actif", nullable = false)
    private boolean actif;

    @Column(name = "designation", nullable = false, length = 25)
    private String designation;

    @Column(name = "occupation")
    private Boolean occupation;

    // Constructeur
    public RestaurantTable() {
    }

    // Constructeur
    public RestaurantTable(String idtable, Boolean actif, String designation, boolean occupation) {
        this.idtable = idtable;
        this.actif = actif;
        this.designation = designation;
        this.occupation = occupation;
    }

    // Getters et Setters
    public String getIdtable() {
        return idtable;
    }

    public void setIdtable(String idtable) {
        this.idtable = idtable;
    }

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getOccupation() {
        return occupation;
    }

    public void setOccupation(Boolean occupation) {
        this.occupation = occupation;
    }
}