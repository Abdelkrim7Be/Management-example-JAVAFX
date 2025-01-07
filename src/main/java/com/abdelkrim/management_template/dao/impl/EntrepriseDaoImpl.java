package com.abdelkrim.management_template.dao.impl;

import com.abdelkrim.management_template.dao.interfaces.EntrepriseDao;
import com.abdelkrim.management_template.models.Entreprise;
import com.abdelkrim.management_template.dao.SingletonConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseDaoImpl implements EntrepriseDao {
    private final Connection connection;

    // Constructor to initialize the connection
    public EntrepriseDaoImpl() {
        this.connection = SingletonConnexionDB.getConnexion();
    }

    @Override
    public void save(Entreprise entreprise) throws SQLException {
        if (entreprise.getId() == 0) {
            String query = "INSERT INTO Entreprise (nom, adresse, telephone, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, entreprise.getNom());
                stmt.setString(2, entreprise.getAdresse());
                stmt.setString(3, entreprise.getTelephone());
                stmt.setString(4, entreprise.getEmail());
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entreprise.setId(generatedKeys.getInt(1)); // Set the generated ID
                }
            }
        } else {
            String query = "UPDATE Entreprise SET nom = ?, adresse = ?, telephone = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, entreprise.getNom());
                stmt.setString(2, entreprise.getAdresse());
                stmt.setString(3, entreprise.getTelephone());
                stmt.setString(4, entreprise.getEmail());
                stmt.setInt(5, entreprise.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Entreprise> findAll() throws SQLException {
        List<Entreprise> entreprises = new ArrayList<>();
        String query = "SELECT * FROM Entreprise";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Entreprise entreprise = new Entreprise(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
                entreprises.add(entreprise);
            }
        }
        return entreprises;
    }

    @Override
    public Entreprise findById(int id) throws SQLException {
        String query = "SELECT * FROM Entreprise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Entreprise(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
            }
        }
        return null;
    }

    @Override
    public void update(Entreprise entreprise) throws SQLException {
        save(entreprise); // The update is handled by the save method itself (insert or update based on ID)
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Entreprise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Entreprise findByName(String name) {
        try {
            String query = "SELECT * FROM Entreprise WHERE nom = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Entreprise(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("adresse"),
                            rs.getString("telephone"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Entreprise> findByKeyword(String keyword) {
        List<Entreprise> entreprises = new ArrayList<>();
        try {
            String query = "SELECT * FROM Entreprise WHERE nom LIKE ? OR adresse LIKE ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                String searchKeyword = "%" + keyword + "%";
                stmt.setString(1, searchKeyword);
                stmt.setString(2, searchKeyword);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Entreprise entreprise = new Entreprise(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("adresse"),
                            rs.getString("telephone"),
                            rs.getString("email")
                    );
                    entreprises.add(entreprise);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entreprises;
    }

    @Override
    public List<Entreprise> findDepartmentsByEntrepriseId(int entrepriseId) {
        List<Entreprise> entreprises = new ArrayList<>();
        try {
            // Assuming there's a "Departments" table or related data, adjust the query accordingly.
            String query = "SELECT * FROM Departments WHERE entreprise_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, entrepriseId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Assuming that departments are linked to an entreprise, adjust accordingly.
                    Entreprise entreprise = new Entreprise(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("adresse"),
                            rs.getString("telephone"),
                            rs.getString("email")
                    );
                    entreprises.add(entreprise);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entreprises;
    }
}
