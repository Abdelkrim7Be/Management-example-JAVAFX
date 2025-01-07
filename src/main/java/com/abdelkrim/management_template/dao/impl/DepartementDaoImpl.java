package com.abdelkrim.management_template.dao.impl;

import com.abdelkrim.management_template.dao.interfaces.DepartementDao;
import com.abdelkrim.management_template.models.Departement;
import com.abdelkrim.management_template.dao.SingletonConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementDaoImpl implements DepartementDao {
    private final Connection connection;

    // Constructor to initialize the connection
    public DepartementDaoImpl() {
        this.connection = SingletonConnexionDB.getConnexion();
    }

    @Override
    public void save(Departement departement) throws SQLException {
        if (departement.getId() == 0) {
            String query = "INSERT INTO Departement (nom, entreprise_id) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, departement.getNom());
                stmt.setInt(2, departement.getEntrepriseId());
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    departement.setId(generatedKeys.getInt(1));
                }
            }
        } else {
            String query = "UPDATE Departement SET nom = ?, entreprise_id = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, departement.getNom());
                stmt.setInt(2, departement.getEntrepriseId());
                stmt.setInt(3, departement.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Departement> findAll() throws SQLException {
        List<Departement> departements = new ArrayList<>();
        String query = "SELECT * FROM Departement";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Departement departement = new Departement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("entreprise_id")
                );
                departements.add(departement);
            }
        }
        return departements;
    }

    @Override
    public Departement findById(int id) throws SQLException {
        String query = "SELECT * FROM Departement WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Departement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("entreprise_id")
                );
            }
        }
        return null;
    }

    @Override
    public void update(Departement departement) throws SQLException {
        save(departement); //La méthode save gère elle-même la mise à jour
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Departement WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Departement findByName(String name) {
        try {
            String query = "SELECT * FROM Departement WHERE nom = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Departement(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getInt("entreprise_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Departement> findByEntrepriseId(int entrepriseId) {
        List<Departement> departements = new ArrayList<>();
        try {
            String query = "SELECT * FROM Departement WHERE entreprise_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, entrepriseId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Departement departement = new Departement(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getInt("entreprise_id")
                    );
                    departements.add(departement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departements;
    }

    @Override
    public List<Departement> findEmployeesByDepartementId(int departementId) {
        List<Departement> departements = new ArrayList<>();
        try {
            String query = "SELECT * FROM Employe WHERE departement_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, departementId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Assuming that employees are linked to a department, adjust the entity as needed.
                    Departement departement = new Departement(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getInt("entreprise_id")
                    );
                    departements.add(departement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departements;
    }
}
