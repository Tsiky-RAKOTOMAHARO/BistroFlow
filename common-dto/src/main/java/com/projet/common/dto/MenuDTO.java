package com.projet.common.dto;

public class MenuDTO {

    //attributs
    private String idplat;
    private Boolean actif;
    private String nomplat;
    private int pu; 

    // constructeurs
    public MenuDTO(){}

    public MenuDTO(String idplat, Boolean actif, String nomplat, int pu){
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
}