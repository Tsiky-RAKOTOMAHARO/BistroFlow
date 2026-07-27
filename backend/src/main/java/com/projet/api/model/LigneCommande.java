package com.projet.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LIGNE_COMMANDE")
public class LigneCommande {
    
    // attributs
    @Id
    @Column(name = "idligne", length = 25)
    private String idligne;

    @ManyToOne
    @JoinColumn(name = "idcom")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "idplat")
    private Menu menu;


    @Column(name = "quantite", nullable = false)
    private int quantite;

    // constructeurs
    public LigneCommande(){}

    public LigneCommande(
        String idligne,
        Commande commande,
        Menu menu,
        int quantite
    ){
        this.idligne = idligne;
        this.commande = commande;
        this.menu = menu;
        this.quantite = quantite;
    }

    // getters
    public String getIdligne(){
        return idligne;
    }
    public Commande getCommande(){
        return commande;
    }
    public Menu getMenu(){
        return menu;
    }
    public int getQuantite(){
        return quantite;
    }

    // setters
    public void setCommande(Commande commande){
        this.commande = commande;
    }
    public void setMenu(Menu menu){
        this.menu = menu;
    }
    public void setQuantite(int quantite){
        this.quantite = quantite;
    }

    public void setIdligne(String idligne) {
        this.idligne = idligne;
    }
}
