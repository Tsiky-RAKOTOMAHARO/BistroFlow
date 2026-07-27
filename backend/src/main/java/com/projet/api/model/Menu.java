package com.projet.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MENU")
public class Menu {

    //attributs
    @Id
    @Column(name = "idplat")
    private String idplat;

    @Column(name = "actif", nullable = false)
    private Boolean actif;

    @Column(name = "nomplat", nullable = false, length = 50)
    private String nomplat;

    @Column(name = "pu", nullable = false)
    private int pu; 

    // constructeurs
    public Menu(){}

    public Menu(String idplat, Boolean actif, String nomplat, int pu){
        this.idplat = idplat;
        this.actif = actif;
        this.nomplat = nomplat;
        this.pu = pu;
    }

    // Getters 
    public String getIdplat(){
        return idplat;
    }
    public Boolean isActif(){
        return actif;
    }
    public String getNomplat(){
        return nomplat;
    }
    public int getPu(){
        return pu;
    }

    // Setters
    public void setActif(Boolean actif){
        this.actif = actif;
    }
    public void setNomplat(String nomplat){
        this.nomplat = nomplat;
    }
    public void setPu(int pu){
        this.pu = pu;
    }

    public void setIdplat(String idplat) {
        this.idplat = idplat;
    }
}