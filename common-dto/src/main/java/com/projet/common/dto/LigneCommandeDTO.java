package com.projet.common.dto;

public class LigneCommandeDTO {
    
    // attributs
    private String idligne;
    private String idcom;
    private String idplat;
    private int quantite;

    // constructeurs
    LigneCommandeDTO(){}

    LigneCommandeDTO(
        String idligne,
        String idcom,
        String idplat,
        int quantite
    ){
        this.idligne = idligne;
        this.idcom = idcom;
        this.idplat = idplat;
        this.quantite = quantite;
    }

    // getters
    public String getIdligne(){
        return idligne;
    }
    public String getIdcom(){
        return idcom;
    }
    public String getIdplat(){
        return idplat;
    }
    public int getQuantite(){
        return quantite;
    }

    // setters
    public void setQuantite(int quantite){
        this.quantite = quantite;
    }
}
