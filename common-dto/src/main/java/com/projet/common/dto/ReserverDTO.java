package com.projet.common.dto;

import java.util.Date;

public class ReserverDTO {

    // attributs
    private String idreserv;
    private String idtable;
    private String nomcli;
    private Date date_de_reserv;
    private Date date_reserve;

    // constructeurs
    public ReserverDTO(){}

    public ReserverDTO(
        String idreserv,
        String idtable,
        String nomcli,
        Date date_de_reserv,
        Date date_reserve
    ){
        this.idreserv = idreserv;
        this.idtable = idtable;
        this.nomcli = nomcli;
        this.date_de_reserv = date_de_reserv;
        this.date_reserve = date_reserve;
    }

    // getters
    public String getIdreserv(){
        return idreserv;
    }
    public String getIdtable(){
        return idtable;
    }
    public String getNomcli(){
        return nomcli;
    }
    public Date getDate_de_reserv(){
        return date_de_reserv;
    }
    public Date getDate_reserve(){
        return date_reserve;
    }

    // setters
    public void setNomcli(String nomcli){
        this.nomcli = nomcli;
    }
    public void setDate_de_reserv(Date date_de_reserve){
        this.date_de_reserv = date_de_reserve;
    }
    public void setDate_reserve(Date date_reserve){
        this.date_reserve = date_reserve;
    }
}