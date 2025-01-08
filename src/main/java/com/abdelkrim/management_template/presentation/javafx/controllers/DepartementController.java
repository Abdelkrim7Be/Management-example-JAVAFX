package com.abdelkrim.management_template.presentation.javafx.controllers;

import com.abdelkrim.management_template.dao.impl.DepartementDaoImpl;
import com.abdelkrim.management_template.dao.impl.EntrepriseDaoImpl;
import com.abdelkrim.management_template.presentation.models.Departement;
import com.abdelkrim.management_template.presentation.models.Entreprise;
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
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class DepartementController {

    private final DepartementDaoImpl departementDaoImpl;
    private final EntrepriseDaoImpl entrepriseDaoImpl;
    private final ObservableList<Departement> departementList = FXCollections.observableArrayList();
    private final ObservableList<String> entrepriseNames = FXCollections.observableArrayList();

    @FXML
    private TableView<Departement> departementTable;

    @FXML
    private TableColumn<Departement, Integer> colId;

    @FXML
    private TableColumn<Departement, String> colNom;

    @FXML
    private TableColumn<Departement, String> colEntreprise;

    @FXML
    private TextField txtNom;

    @FXML
    private ComboBox<String> comboEntreprise;

    @FXML
    private Label lblStatus;

    public DepartementController() {
        this.departementDaoImpl = new DepartementDaoImpl();  // Using DAO directly
        this.entrepriseDaoImpl = new EntrepriseDaoImpl();  // Using DAO directly
    }

    @FXML
    public void initialize() {
        // Initialize table columns using getters
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colEntreprise.setCellValueFactory(cellData -> new SimpleStringProperty(getEntrepriseName(cellData.getValue().getEntrepriseId())));

        // Custom cell factory to center the content
        Callback<TableColumn<Departement, Integer>, TableCell<Departement, Integer>> cellFactoryInteger = column -> {
            TableCell<Departement, Integer> cell = new TableCell<Departement, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
            return cell;
        };

        Callback<TableColumn<Departement, String>, TableCell<Departement, String>> cellFactoryString = column -> {
            TableCell<Departement, String> cell = new TableCell<Departement, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
            return cell;
        };

        colId.setCellFactory(cellFactoryInteger);
        colNom.setCellFactory(cellFactoryString);
        colEntreprise.setCellFactory(cellFactoryString);

        // Load data into the table
        loadDepartementData();
        loadEntrepriseData();
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

    private void loadEntrepriseData() {
        entrepriseNames.clear();
        try {
            List<Entreprise> entreprises = entrepriseDaoImpl.findAll();
            for (Entreprise entreprise : entreprises) {
                entrepriseNames.add(entreprise.getNom());
            }
            comboEntreprise.setItems(entrepriseNames);
        } catch (Exception e) {
            lblStatus.setText("Erreur lors de la récupération des entreprises : " + e.getMessage());
        }
    }

    private String getEntrepriseName(int entrepriseId) {
        try {
            Entreprise entreprise = entrepriseDaoImpl.findById(entrepriseId);
            return entreprise != null ? entreprise.getNom() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private int getEntrepriseId(String entrepriseName) {
        try {
            Entreprise entreprise = entrepriseDaoImpl.findByName(entrepriseName);
            return entreprise != null ? entreprise.getId() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @FXML
    private void handleAddDepartement(ActionEvent event) {
        try {
            int entrepriseId = getEntrepriseId(comboEntreprise.getValue());  // Get entreprise ID from name

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
                selectedDepartement.setEntrepriseId(getEntrepriseId(comboEntreprise.getValue()));

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
            comboEntreprise.setValue(getEntrepriseName(selectedDepartement.getEntrepriseId())); // Display entreprise name
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage) departementTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/abdelkrim/management_template/views/dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void clearForm() {
        txtNom.clear();
        comboEntreprise.setValue(null);
    }
}
