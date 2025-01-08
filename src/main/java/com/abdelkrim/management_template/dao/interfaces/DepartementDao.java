package com.abdelkrim.management_template.dao.interfaces;

import com.abdelkrim.management_template.presentation.models.Departement;
import java.util.List;

public interface DepartementDao extends Dao<Departement> {
    //L'EntrepriseDao étend désormais Dao<Entreprise>, ce qui lui permet d'hériter automatiquement
    // des méthodes save(), findAll(), findById(), update() et delete().
    Departement findByName(String name);
    List<Departement> findByEntrepriseId(int entrepriseId);
    List<Departement> findEmployeesByDepartementId(int departementId);
}
