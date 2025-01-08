package com.abdelkrim.management_template.presentation.javafx.controllers;

import com.abdelkrim.management_template.dao.impl.EmployeDaoImpl;
import com.abdelkrim.management_template.presentation.models.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.util.List;

public class EmployeController {

    private final EmployeDaoImpl employeDaoImpl;
    private final ObservableList<Employe> employeList = FXCollections.observableArrayList();

    @FXML
    private TableView<Employe> employeTable;

    @FXML
    private TableColumn<Employe, Integer> colId;

    @FXML
    private TableColumn<Employe, String> colNom;

    @FXML
    private TableColumn<Employe, String> colPoste;

    @FXML
    private TableColumn<Employe, BigDecimal> colSalaire;

    @FXML
    private TableColumn<Employe, Integer> colDepartementId;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPoste;

    @FXML
    private TextField txtSalaire;

    @FXML
    private TextField txtDepartementId;

    @FXML
    private Label lblStatus;

    public EmployeController() {
        this.employeDaoImpl = new EmployeDaoImpl(); // Using DAO directly
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colPoste.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPoste()));
        colSalaire.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSalaire()));
        colDepartementId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDepartementId()));

        loadEmployeData();
    }

    private void loadEmployeData() {
        employeList.clear();
        try {
            List<Employe> employes = employeDaoImpl.findAll();
            employeList.addAll(employes);
            employeTable.setItems(employeList);
        } catch (Exception e) {
            lblStatus.setText("Erreur lors de la récupération des employés : " + e.getMessage());
        }
    }

    @FXML
    private void handleAddEmploye(ActionEvent event) {
        try {
            BigDecimal salaire = new BigDecimal(txtSalaire.getText()); // Convert salaire to BigDecimal

            int departementId = Integer.parseInt(txtDepartementId.getText());  // Convert to int

            Employe newEmploye = new Employe(
                    0, // Assuming 0 for new employe (ID will be generated)
                    txtNom.getText(),
                    txtPoste.getText(),
                    salaire,
                    departementId
            );
            employeDaoImpl.save(newEmploye); // Direct DAO call
            lblStatus.setText("Employé ajouté avec succès !");
            loadEmployeData();
            clearForm();
        } catch (NumberFormatException e) {
            lblStatus.setText("Erreur : Salaire ou ID du département invalide.");
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateEmploye(ActionEvent event) {
        try {
            Employe selectedEmploye = employeTable.getSelectionModel().getSelectedItem();
            if (selectedEmploye != null) {
                selectedEmploye.setNom(txtNom.getText());
                selectedEmploye.setPoste(txtPoste.getText());
                selectedEmploye.setSalaire(new BigDecimal(txtSalaire.getText()));
                selectedEmploye.setDepartementId(Integer.parseInt(txtDepartementId.getText()));

                employeDaoImpl.update(selectedEmploye); // Direct DAO call
                lblStatus.setText("Employé modifié avec succès !");
                loadEmployeData();
                clearForm();
            } else {
                lblStatus.setText("Veuillez sélectionner un employé.");
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Erreur : Salaire ou ID du département invalide.");
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteEmploye(ActionEvent event) {
        try {
            Employe selectedEmploye = employeTable.getSelectionModel().getSelectedItem();
            if (selectedEmploye != null) {
                employeDaoImpl.delete(selectedEmploye.getId()); // Direct DAO call
                lblStatus.setText("Employé supprimé avec succès !");
                loadEmployeData();
            } else {
                lblStatus.setText("Veuillez sélectionner un employé.");
            }
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleSelectEmploye() {
        Employe selectedEmploye = employeTable.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            txtNom.setText(selectedEmploye.getNom());
            txtPoste.setText(selectedEmploye.getPoste());
            txtSalaire.setText(selectedEmploye.getSalaire().toString());
            txtDepartementId.setText(String.valueOf(selectedEmploye.getDepartementId())); // Display departementId
        }
    }

    private void clearForm() {
        txtNom.clear();
        txtPoste.clear();
        txtSalaire.clear();
        txtDepartementId.clear();
    }
}
