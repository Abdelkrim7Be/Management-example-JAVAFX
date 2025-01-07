package com.abdelkrim.management_template.dao.interfaces;

import com.abdelkrim.management_template.models.Entreprise;
import java.util.List;

public interface EntrepriseDao extends Dao<Entreprise> {
    //L'EntrepriseDao étend désormais Dao<Entreprise>, ce qui lui permet d'hériter automatiquement
    // des méthodes save(), findAll(), findById(), update() et delete().
    Entreprise findByName(String name);
    List<Entreprise> findByKeyword(String keyword);
    List<Entreprise> findDepartmentsByEntrepriseId(int entrepriseId);
}
