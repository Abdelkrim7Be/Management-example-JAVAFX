package com.abdelkrim.management_template.dao.interfaces;

import com.abdelkrim.management_template.models.Departement;
import java.util.List;

public interface DepartementDao extends Dao<Departement> {
    void create(Departement departement);
    void update(Departement departement);
    Departement findByName(String name);
    List<Departement> findByEntrepriseId(int entrepriseId);
    List<Departement> findEmployeesByDepartementId(int departementId);
    void delete(int departementId);
}
