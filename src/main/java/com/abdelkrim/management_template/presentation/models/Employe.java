package com.abdelkrim.management_template.presentation.models;

import java.math.BigDecimal;

public class Employe {
    private int id;
    private String nom;
    private String poste;
    private BigDecimal salaire;
    private int departementId;

    public Employe(int id, String nom, String poste, BigDecimal salaire, int departementId) {
        this.id = id;
        this.nom = nom;
        this.poste = poste;
        this.salaire = salaire;
        this.departementId = departementId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPoste() {
        return poste;
    }
    public void setPoste(String poste) {
        this.poste = poste;
    }
    public BigDecimal getSalaire() {
        return salaire;
    }
    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }
    public int getDepartementId() {
        return departementId;
    }
    public void setDepartementId(int departementId) {
        this.departementId = departementId;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", poste='" + poste + '\'' +
                ", salaire=" + salaire +
                ", departementId=" + departementId +
                '}';
    }
}
