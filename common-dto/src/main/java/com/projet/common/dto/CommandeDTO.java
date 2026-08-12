package com.projet.common.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandeDTO {

    private String idcom;
    private String idtable;
    private String nomcli;
    private String typecom;
    private LocalDateTime datecom;
    private Boolean paye;
    
    // Liste des lignes de commande associées
    private List<LigneCommandeDTO> lignes = new ArrayList<>();

    public CommandeDTO() {}

    public CommandeDTO(
        String idcom,
        String idtable,
        String nomcli,
        String typecom,
        LocalDateTime datecom,
        Boolean paye,
        List<LigneCommandeDTO> lignes
    ) {
        this.idcom = idcom;
        this.idtable = idtable;
        this.nomcli = nomcli;
        this.typecom = typecom;
        this.datecom = datecom;
        this.paye = paye;
        if (lignes != null) {
            this.lignes = lignes;
        }
    }

    // Getters
    public String getIdcom() { return idcom; }
    public String getIdtable() { return idtable; }
    public String getNomcli() { return nomcli; }
    public String getTypecom() { return typecom; }
    public LocalDateTime getDatecom() { return datecom; }
    public Boolean isPaye() { return paye; }
    public List<LigneCommandeDTO> getLignes() { return lignes; }

    // Setters
    public void setIdcom(String idcom) { this.idcom = idcom; }
    public void setIdtable(String idtable) { this.idtable = idtable; }
    public void setNomcli(String nomcli) { this.nomcli = nomcli; }
    public void setTypecom(String typecom) { this.typecom = typecom; }
    public void setDatecom(LocalDateTime datecom) { this.datecom = datecom; }
    public void setPaye(Boolean paye) { this.paye = paye; }
    public void setLignes(List<LigneCommandeDTO> lignes) { this.lignes = lignes; }
}