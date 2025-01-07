package com.abdelkrim.management_template.metier.impl;

import com.abdelkrim.management_template.dao.interfaces.EntrepriseDao;
import com.abdelkrim.management_template.models.Entreprise;
import com.abdelkrim.management_template.metier.interfaces.IEntrepriseMetier;

import java.util.List;

public class EntrepriseMetierImpl implements IEntrepriseMetier {

    private final EntrepriseDao EntrepriseDao;

    public EntrepriseMetierImpl(EntrepriseDao entrepriseDao) {
        this.EntrepriseDao = entrepriseDao;
    }

    @Override
    public void save(Entreprise entreprise) throws Exception {
        if (entreprise == null) {
            throw new Exception("Entreprise cannot be null");
        }
        EntrepriseDao.save(entreprise);
    }

    @Override
    public List<Entreprise> findAll() throws Exception {
        return EntrepriseDao.findAll();
    }

    @Override
    public Entreprise findById(int id) throws Exception {
        Entreprise entreprise = EntrepriseDao.findById(id);
        if (entreprise == null) {
            throw new Exception("Entreprise with ID " + id + " not found");
        }
        return entreprise;
    }

    @Override
    public Entreprise findByName(String name) throws Exception {
        Entreprise entreprise = EntrepriseDao.findByName(name);
        if (entreprise == null) {
            throw new Exception("Entreprise with name " + name + " not found");
        }
        return entreprise;
    }

    @Override
    public List<Entreprise> findByKeyword(String keyword) throws Exception {
        return EntrepriseDao.findByKeyword(keyword);
    }

    @Override
    public List<Entreprise> findDepartmentsByEntrepriseId(int entrepriseId) throws Exception {
        return EntrepriseDao.findDepartmentsByEntrepriseId(entrepriseId);
    }

    @Override
    public void delete(int id) throws Exception {
        Entreprise entreprise = EntrepriseDao.findById(id);
        if (entreprise == null) {
            throw new Exception("Entreprise with ID " + id + " does not exist");
        }
        EntrepriseDao.delete(id);
    }

    @Override
    public void update(Entreprise entreprise) throws Exception {
        if (entreprise == null) {
            throw new Exception("Entreprise cannot be null");
        }

        Entreprise existingEntreprise = EntrepriseDao.findById(entreprise.getId());
        if (existingEntreprise == null) {
            throw new Exception("Entreprise with ID " + entreprise.getId() + " not found");
        }

        EntrepriseDao.update(entreprise);
    }
}
