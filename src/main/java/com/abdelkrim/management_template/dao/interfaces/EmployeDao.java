package com.abdelkrim.management_template.dao.interfaces;

import com.abdelkrim.management_template.models.Employe;
import java.util.List;

public interface EmployeDao extends Dao<Employe> {
    void create(Employe employe);
    void update(Employe employe);
    Employe findByName(String name);
    List<Employe> findByDepartementId(int departementId);
    List<Employe> findByPosition(String position);
    void delete(int employeId);
}
