package com.abdelkrim.management_template.presentation.javafx.controllers;

import com.abdelkrim.management_template.metier.interfaces.IEntrepriseMetier;
import com.abdelkrim.management_template.metier.impl.EntrepriseMetierImpl;
import com.abdelkrim.management_template.presentation.models.Entreprise;
import com.abdelkrim.management_template.dao.impl.EntrepriseDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


import java.util.List;

public class EntrepriseController {

    private final IEntrepriseMetier entrepriseMetier;
    private final ObservableList<Entreprise> entrepriseList = FXCollections.observableArrayList();


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
    private Label lblStatus;

    public EntrepriseController() {
        // Instantiate the DAO implementation (make sure EntrepriseDaoImpl is properly implemented)
        EntrepriseDaoImpl entrepriseDaoImpl = new EntrepriseDaoImpl();
        this.entrepriseMetier = new EntrepriseMetierImpl(entrepriseDaoImpl); // Pass DAO implementation to Metier
    }


    @FXML
    public void initialize() {
        // Initialize table columns using getters
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        colTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Load data into the table
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
        }
    }

    private void clearForm() {
        txtNom.clear();
        txtAdresse.clear();
        txtTelephone.clear();
        txtEmail.clear();
    }
}
