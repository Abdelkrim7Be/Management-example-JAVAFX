package com.abdelkrim.management_template.dao.interfaces;

import com.abdelkrim.management_template.models.Entreprise;
import java.util.List;

public interface EntrepriseDao extends Dao<Entreprise> {
    void create(Entreprise entreprise);
    void save(Entreprise entreprise);
    Entreprise findByName(String name);
    List<Entreprise> findByKeyword(String keyword);
    List<Entreprise> findDepartmentsByEntrepriseId(int entrepriseId);
    void delete(int entrepriseId);
}
