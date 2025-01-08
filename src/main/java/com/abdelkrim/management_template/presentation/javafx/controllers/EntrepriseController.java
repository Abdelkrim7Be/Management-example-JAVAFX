package com.abdelkrim.management_template.presentation.javafx.controllers;

import com.abdelkrim.management_template.metier.interfaces.IEntrepriseMetier;
import com.abdelkrim.management_template.metier.impl.EntrepriseMetierImpl;
import com.abdelkrim.management_template.presentation.models.Entreprise;
import com.abdelkrim.management_template.presentation.models.Employe;
import com.abdelkrim.management_template.presentation.models.Departement;
import com.abdelkrim.management_template.dao.impl.EntrepriseDaoImpl;
import com.abdelkrim.management_template.dao.impl.EmployeDaoImpl;
import com.abdelkrim.management_template.dao.impl.DepartementDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EntrepriseController {

    private final IEntrepriseMetier entrepriseMetier;
    private final EmployeDaoImpl employeDaoImpl;
    private final DepartementDaoImpl departementDaoImpl;
    private final ObservableList<Entreprise> entrepriseList = FXCollections.observableArrayList();
    private final ObservableList<Employe> employeList = FXCollections.observableArrayList();
    private final ObservableList<Departement> departementList = FXCollections.observableArrayList();

    @FXML
    private TableView<Entreprise> entrepriseTable;

    @FXML
    private TableColumn<Entreprise, Integer> colId;

    @FXML
    private TableColumn<Entreprise, String> colNom;

    @FXML
    private TableColumn<Entreprise, String> colAdresse;

    @FXML
    private TableColumn<Entreprise, String> colTelephone;

    @FXML
    private TableColumn<Entreprise, String> colEmail;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtAdresse;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TableView<Employe> employeTable;

    @FXML
    private TableColumn<Employe, Integer> colEmployeId;

    @FXML
    private TableColumn<Employe, String> colEmployeNom;

    @FXML
    private TableColumn<Employe, String> colEmployePoste;

    @FXML
    private TableColumn<Employe, String> colEmployeSalaire;

    @FXML
    private TableColumn<Employe, String> colEmployeDepartement;

    @FXML
    private TableView<Departement> departementTable;

    @FXML
    private TableColumn<Departement, Integer> colDepartementId;

    @FXML
    private TableColumn<Departement, String> colDepartementNom;

    @FXML
    private Label lblStatus;

    public EntrepriseController() {
        EntrepriseDaoImpl entrepriseDaoImpl = new EntrepriseDaoImpl();
        this.entrepriseMetier = new EntrepriseMetierImpl(entrepriseDaoImpl); // Pass DAO implementation to Metier
        this.employeDaoImpl = new EmployeDaoImpl();
        this.departementDaoImpl = new DepartementDaoImpl();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        colTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        colEmployeId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colEmployeNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colEmployePoste.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPoste()));
        colEmployeSalaire.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSalaire().toString()));
        colEmployeDepartement.setCellValueFactory(cellData -> new SimpleStringProperty(getDepartementName(cellData.getValue().getDepartementId())));

        colDepartementId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colDepartementNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));

        loadEntrepriseData();
    }

    private void loadEntrepriseData() {
        entrepriseList.clear();
        try {
            List<Entreprise> entreprises = entrepriseMetier.findAll();
            entrepriseList.addAll(entreprises);
            entrepriseTable.setItems(entrepriseList);
        } catch (Exception e) {
            lblStatus.setText("Erreur lors de la récupération des entreprises : " + e.getMessage());
        }
    }

    private void loadEmployeData(int entrepriseId) {
        employeList.clear();
        try {
            List<Employe> employes = employeDaoImpl.findByEntrepriseId(entrepriseId);
            employeList.addAll(employes);
            employeTable.setItems(employeList);
        } catch (Exception e) {
            lblStatus.setText("Erreur lors de la récupération des employés : " + e.getMessage());
        }
    }

    private void loadDepartementData(int entrepriseId) {
        departementList.clear();
        try {
            List<Departement> departements = departementDaoImpl.findByEntrepriseId(entrepriseId);
            departementList.addAll(departements);
            departementTable.setItems(departementList);
        } catch (Exception e) {
            lblStatus.setText("Erreur lors de la récupération des départements : " + e.getMessage());
        }
    }

    private String getDepartementName(int departementId) {
        try {
            Departement departement = departementDaoImpl.findById(departementId);
            return departement != null ? departement.getNom() : "";
        } catch (Exception e) {
            return "";
        }
    }

    @FXML
    private void handleAddEntreprise(ActionEvent event) {
        try {
            Entreprise newEntreprise = new Entreprise(
                    0,
                    txtNom.getText(),
                    txtAdresse.getText(),
                    txtTelephone.getText(),
                    txtEmail.getText()
            );
            entrepriseMetier.save(newEntreprise);
            lblStatus.setText("Entreprise ajoutée avec succès !");
            loadEntrepriseData();
            clearForm();
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateEntreprise(ActionEvent event) {
        try {
            Entreprise selectedEntreprise = entrepriseTable.getSelectionModel().getSelectedItem();
            if (selectedEntreprise != null) {
                selectedEntreprise.setNom(txtNom.getText());
                selectedEntreprise.setAdresse(txtAdresse.getText());
                selectedEntreprise.setTelephone(txtTelephone.getText());
                selectedEntreprise.setEmail(txtEmail.getText());

                entrepriseMetier.update(selectedEntreprise);
                lblStatus.setText("Entreprise modifiée avec succès !");
                loadEntrepriseData();
                clearForm();
            } else {
                lblStatus.setText("Veuillez sélectionner une entreprise.");
            }
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteEntreprise(ActionEvent event) {
        try {
            Entreprise selectedEntreprise = entrepriseTable.getSelectionModel().getSelectedItem();
            if (selectedEntreprise != null) {
                entrepriseMetier.delete(selectedEntreprise.getId());
                lblStatus.setText("Entreprise supprimée avec succès !");
                loadEntrepriseData();
            } else {
                lblStatus.setText("Veuillez sélectionner une entreprise.");
            }
        } catch (Exception e) {
            lblStatus.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void handleSelectEntreprise() {
        Entreprise selectedEntreprise = entrepriseTable.getSelectionModel().getSelectedItem();
        if (selectedEntreprise != null) {
            txtNom.setText(selectedEntreprise.getNom());
            txtAdresse.setText(selectedEntreprise.getAdresse());
            txtTelephone.setText(selectedEntreprise.getTelephone());
            txtEmail.setText(selectedEntreprise.getEmail());
            loadEmployeData(selectedEntreprise.getId());
            loadDepartementData(selectedEntreprise.getId());
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage) entrepriseTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/abdelkrim/management_template/views/dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void clearForm() {
        txtNom.clear();
        txtAdresse.clear();
        txtTelephone.clear();
        txtEmail.clear();
    }
}
