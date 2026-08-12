package com.projet.common.dto;

public class TableDTO {

    private String idtable;
    private boolean actif;
    private String designation;
    private boolean occupation;

    // Constructeur
    public TableDTO() {
    }

    // Constructeur 
    public TableDTO(String idtable, boolean actif, String designation, Boolean occupation) {
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