package com.projet.common.dto;

import java.time.LocalDateTime;

public class CommandeDTO {

    // attributs
    private String idcom;
    private String idtable;
    private String nomcli;
    private String typecom;
    private LocalDateTime datecom;
    private Boolean paye;

    // constructeurs
    public CommandeDTO(){}

    public CommandeDTO(
        String idcom,
        String idtable,
        String nomcli,
        String typecom,
        LocalDateTime datecom,
        Boolean paye
    ){
        this.idcom = idcom;
        this.idtable = idtable;
        this.nomcli = nomcli;
        this.typecom = typecom;
        this.datecom = datecom;
        this.paye = paye;
    }

    // getters
    public String getIdcom(){
        return idcom;
    }
    public String getIdtable(){
        return idtable;
    }
    public String getNomcli(){
        return nomcli;
    }
    public String getTypecom(){
        return typecom;
    }
    public LocalDateTime getDatecom(){
        return datecom;
    }
    public Boolean isPaye(){
        return paye;
    }

    // setters
    public void setNomcli(String nomcli){
        this.nomcli = nomcli;
    }
    public void setTypecom(String typecom){
        this.typecom = typecom;
    }
    public void setDatecom(LocalDateTime datecom){
        this.datecom = datecom;
    }
    public void setPaye(Boolean paye){
        this.paye = paye;
    }

    public void setIdcom(String idcom){
        this.idcom = idcom;
    }

    public void setIdtable(String idtable){
        this.idtable = idtable;
    }
}