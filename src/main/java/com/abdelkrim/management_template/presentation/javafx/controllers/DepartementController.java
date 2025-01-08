package com.abdelkrim.management_template.presentation.javafx.controllers;

import com.abdelkrim.management_template.dao.impl.DepartementDaoImpl;
import com.abdelkrim.management_template.presentation.models.Departement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class DepartementController {

    private final DepartementDaoImpl departementDaoImpl;
    private final ObservableList<Departement> departementList = FXCollections.observableArrayList();

    @FXML
    private TableView<Departement> departementTable;

    @FXML
    private TableColumn<Departement, Integer> colId;

    @FXML
    private TableColumn<Departement, String> colNom;

    @FXML
    private TableColumn<Departement, Integer> colEntrepriseId;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEntrepriseId;

    @FXML
    private Label lblStatus;

    public DepartementController() {
        this.departementDaoImpl = new DepartementDaoImpl();  // Using DAO directly
    }

    @FXML
    public void initialize() {
        // Initialize table columns using getters
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colEntrepriseId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEntrepriseId()));

        // Load data into the table
        loadDepartementData();
    }

    private void loadDepartementData() {
        departementList.clear();
        try {
            List<Departement> departements = departementDaoImpl.findAll(); // Direct DAO call
            departementList.addAll(departements);
            departementTable.setItems(departementList);
        } catch (Exception e) {
            lblStatus.setText("Erreur lors de la récupération des départements : " + e.getMessage());
        }
    }

    @FXML
    private void handleAddDepartement(ActionEvent event) {
        try {
            int entrepriseId = Integer.parseInt(txtEntrepriseId.getText());  // Convert to int

            Departement newDepartement = new Departement(
                    0,
                    txtNom.getText(),
                    entrepriseId
            );
            departementDaoImpl.save(newDepartement); // Direct DAO call
            lblStatus.setText("Département ajouté avec succès !");
            loadDepartementData();
            clearForm();
        } catch (NumberFormatException e) {
            lblStatus.setText("Erreur : ID de l'entreprise invalide.");
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateDepartement(ActionEvent event) {
        try {
            Departement selectedDepartement = departementTable.getSelectionModel().getSelectedItem();
            if (selectedDepartement != null) {
                selectedDepartement.setNom(txtNom.getText());
                selectedDepartement.setEntrepriseId(Integer.parseInt(txtEntrepriseId.getText()));

                departementDaoImpl.update(selectedDepartement); // Direct DAO call
                lblStatus.setText("Département modifié avec succès !");
                loadDepartementData();
                clearForm();
            } else {
                lblStatus.setText("Veuillez sélectionner un département.");
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Erreur : ID de l'entreprise invalide.");
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteDepartement(ActionEvent event) {
        try {
            Departement selectedDepartement = departementTable.getSelectionModel().getSelectedItem();
            if (selectedDepartement != null) {
                departementDaoImpl.delete(selectedDepartement.getId()); // Direct DAO call
                lblStatus.setText("Département supprimé avec succès !");
                loadDepartementData();
            } else {
                lblStatus.setText("Veuillez sélectionner un département.");
            }
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleSelectDepartement() {
        Departement selectedDepartement = departementTable.getSelectionModel().getSelectedItem();
        if (selectedDepartement != null) {
            txtNom.setText(selectedDepartement.getNom());
            txtEntrepriseId.setText(String.valueOf(selectedDepartement.getEntrepriseId())); // Display entrepriseId
        }
    }

    private void clearForm() {
        txtNom.clear();
        txtEntrepriseId.clear();
    }
}
