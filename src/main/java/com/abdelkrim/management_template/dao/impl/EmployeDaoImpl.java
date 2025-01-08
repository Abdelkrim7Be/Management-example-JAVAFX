package com.abdelkrim.management_template.dao.impl;

import com.abdelkrim.management_template.dao.interfaces.EmployeDao;
import com.abdelkrim.management_template.presentation.models.Employe;
import com.abdelkrim.management_template.dao.SingletonConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDaoImpl implements EmployeDao {
    private final Connection connection;

    // Constructor to initialize the connection
    public EmployeDaoImpl() {
        this.connection = SingletonConnexionDB.getConnexion();
    }

    @Override
    public void save(Employe employe) throws SQLException {
        if (employe.getId() == 0) {
            String query = "INSERT INTO Employe (nom, poste, salaire, departement_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, employe.getNom());
                stmt.setString(2, employe.getPoste());
                stmt.setBigDecimal(3, employe.getSalaire());
                stmt.setInt(4, employe.getDepartementId());
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    employe.setId(generatedKeys.getInt(1));
                }
            }
        } else {
            String query = "UPDATE Employe SET nom = ?, poste = ?, salaire = ?, departement_id = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, employe.getNom());
                stmt.setString(2, employe.getPoste());
                stmt.setBigDecimal(3, employe.getSalaire());
                stmt.setInt(4, employe.getDepartementId());
                stmt.setInt(5, employe.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Employe> findAll() throws SQLException {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM Employe";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employe employe = new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("poste"),
                        rs.getBigDecimal("salaire"),
                        rs.getInt("departement_id")
                );
                employes.add(employe);
            }
        }
        return employes;
    }

    @Override
    public Employe findById(int id) throws SQLException {
        String query = "SELECT * FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("poste"),
                        rs.getBigDecimal("salaire"),
                        rs.getInt("departement_id")
                );
            }
        }
        return null;
    }

    @Override
    public void update(Employe employe) throws SQLException {
        save(employe); //La méthode save gère elle-même la mise à jour
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Employe findByName(String name) {
        try {
            String query = "SELECT * FROM Employe WHERE nom = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Employe(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("poste"),
                            rs.getBigDecimal("salaire"),
                            rs.getInt("departement_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employe> findByDepartementId(int departementId) {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM Employe WHERE departement_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, departementId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employe employe = new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("poste"),
                        rs.getBigDecimal("salaire"),
                        rs.getInt("departement_id")
                );
                employes.add(employe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }

    @Override
    public List<Employe> findByEntrepriseId(int entrepriseId) {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT e.* FROM Employe e JOIN Departement d ON e.departement_id = d.id WHERE d.entreprise_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, entrepriseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employe employe = new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("poste"),
                        rs.getBigDecimal("salaire"),
                        rs.getInt("departement_id")
                );
                employes.add(employe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }
}
