package com.abdelkrim.management_template.presentation.models;

public class Departement {
    private int id;
    private String nom;
    private int entrepriseId;

    public Departement(int id, String nom, int entrepriseId) {
        this.id = id;
        this.nom = nom;
        this.entrepriseId = entrepriseId;
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
    public int getEntrepriseId() {
        return entrepriseId;
    }
    public void setEntrepriseId(int entrepriseId) {
        this.entrepriseId = entrepriseId;
    }
    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", entrepriseId=" + entrepriseId +
                '}';
    }
}
