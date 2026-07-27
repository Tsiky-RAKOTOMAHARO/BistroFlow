package com.projet.common.dto;

import java.time.LocalDateTime;

public class ReserverDTO {

    // attributs
    private String idreserv;
    private String idtable;
    private String nomcli;
    private LocalDateTime date_de_reserv;
    private LocalDateTime date_reserve;

    // constructeurs
    public ReserverDTO(){}

    public ReserverDTO(
        String idreserv,
        String idtable,
        String nomcli,
        LocalDateTime date_de_reserv,
        LocalDateTime date_reserve
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
    public LocalDateTime getDate_de_reserv(){
        return date_de_reserv;
    }
    public LocalDateTime getDate_reserve(){
        return date_reserve;
    }

    // setters
    public void setNomcli(String nomcli){
        this.nomcli = nomcli;
    }
    public void setDate_de_reserv(LocalDateTime date_de_reserve){
        this.date_de_reserv = date_de_reserve;
    }
    public void setDate_reserve(LocalDateTime date_reserve){
        this.date_reserve = date_reserve;
    }

    public void setIdreserv(String idreserv) {
        this.idreserv = idreserv;
    }
    public void setIdtable(String idtable){
        this.idtable = idtable; 
    }
}