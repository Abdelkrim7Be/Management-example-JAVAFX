package com.abdelkrim.management_template.dao.interfaces;

import com.abdelkrim.management_template.presentation.models.Employe;

import java.util.List;

public interface EmployeDao extends Dao<Employe> {
    //L'EntrepriseDao étend désormais Dao<Entreprise>, ce qui lui permet d'hériter automatiquement
    // des méthodes save(), findAll(), findById(), update() et delete().
    Employe findByName(String name);
    List<Employe> findByDepartementId(int departementId);
    List<Employe> findByEntrepriseId(int entrepriseId);
}
