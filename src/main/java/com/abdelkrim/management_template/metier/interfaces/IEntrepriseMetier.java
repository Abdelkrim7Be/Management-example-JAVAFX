package com.abdelkrim.management_template.metier.interfaces;

import com.abdelkrim.management_template.presentation.models.Entreprise;

import java.util.List;

public interface IEntrepriseMetier {
    void save(Entreprise entreprise) throws Exception;
    List<Entreprise> findAll() throws Exception;
    Entreprise findById(int id) throws Exception;
    Entreprise findByName(String name) throws Exception;
    List<Entreprise> findByKeyword(String keyword) throws Exception;
    List<Entreprise> findDepartmentsByEntrepriseId(int entrepriseId) throws Exception;
    void update(Entreprise entreprise) throws Exception;
    void delete(int id) throws Exception;
}
