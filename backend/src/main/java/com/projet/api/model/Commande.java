package com.projet.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMMANDE")
public class Commande{

    // attributs
    @Id
    @Column(name = "idcom")
    private String idcom;

    @ManyToOne
    @JoinColumn(name = "idtable")
    private RestaurantTable restaurantTable;

    @Column(name="nomcli", nullable = false, length = 150)
    private String nomcli;

    @Column(name = "typecom", nullable = false, length = 25)
    private String typecom;

    @Column(name = "datecom", nullable = false)
    private LocalDateTime datecom;

    @Column(name = "paye")
    private Boolean paye;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignes = new ArrayList<>();

    // constructeurs
    public Commande(){}

    public Commande(
        String idcom,
        RestaurantTable restaurantTable,
        String nomcli,
        String typecom,
        LocalDateTime datecom,
        Boolean paye
    ){
        this.idcom = idcom;
        this.restaurantTable = restaurantTable;
        this.nomcli = nomcli;
        this.typecom = typecom;
        this.datecom = datecom;
        this.paye = paye;
    }

    // getters
    public String getIdcom(){
        return idcom;
    }
    public RestaurantTable getRestaurantTable(){
        return restaurantTable;
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
    public void setRestaurantTable(RestaurantTable restaurantTable){
        this.restaurantTable = restaurantTable;
    }
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

    public void setIdcom(String idcom) {
        this.idcom = idcom;
    }
}