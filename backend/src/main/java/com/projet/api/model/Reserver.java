package com.projet.api.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "RESERVER")
public class Reserver{

    // attributs
    @Id
    @Column(name = "idreserv")
    private String idreserv;

    @ManyToOne
    @JoinColumn(name = "idtable")
    private RestaurantTable restaurantTable;

    @Column(name = "nomcli", nullable = false, length = 150)
    private String nomcli;

    @Column(name = "date_de_reserv")
    private LocalDateTime date_de_reserv;

    @Column(name = "date_reserve")
    private LocalDateTime date_reserve;

    // constructeurs
    public Reserver(){}

    public Reserver(
        String idreserv,
        RestaurantTable restaurantTable,
        String nomcli,
        LocalDateTime date_de_reserv,
        LocalDateTime date_reserve
    ){
        this.idreserv = idreserv;
        this.restaurantTable = restaurantTable;
        this.nomcli = nomcli;
        this.date_de_reserv = date_de_reserv;
        this.date_reserve = date_reserve;
    }

    // getters
    public String getIdreserv(){
        return idreserv;
    }
    public RestaurantTable getRestaurantTable(){
        return restaurantTable;
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
    public void setRestaurantTable(RestaurantTable restaurantTable){
        this.restaurantTable = restaurantTable;
    }
    public void setNomcli(String nomcli){
        this.nomcli = nomcli;
    }
    public void setDate_de_reserv(LocalDateTime date_de_reserve){
        this.date_de_reserv = date_de_reserve;
    }
    public void setDate_reserve(LocalDateTime date_reserve){
        this.date_reserve = date_reserve;
    }
}