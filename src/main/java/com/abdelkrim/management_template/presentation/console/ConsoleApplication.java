package com.abdelkrim.management_template.presentation.console;


import com.abdelkrim.management_template.dao.impl.EntrepriseDaoImpl;
import com.abdelkrim.management_template.dao.interfaces.EntrepriseDao;
import com.abdelkrim.management_template.metier.impl.EntrepriseMetierImpl;
import com.abdelkrim.management_template.metier.interfaces.IEntrepriseMetier;
import com.abdelkrim.management_template.models.Entreprise;

import java.util.List;

public class ConsoleApplication {

    public static void main(String[] args) {

        //On va créer la DAO et les  objets METIER
        EntrepriseDao entrepriseDao = new EntrepriseDaoImpl();
        IEntrepriseMetier entrepriseMetier = new EntrepriseMetierImpl(entrepriseDao);

        // Maintenant, je vais créer un exemple de l'objet Entreprise pour tester la logique business
        Entreprise entreprise = new Entreprise(0, "Tech Solutions", "1234 Silicon Valley", "123-456-7890", "contact@techsolutions.com");

        try {
            // Step 3: Test Save Method
            System.out.println("Saving Entreprise...");
            entrepriseMetier.save(entreprise);
            System.out.println("Entreprise saved: " + entreprise);

            // Step 4: Retrieve and print all Enterprises
            List<Entreprise> entreprises = entrepriseMetier.findAll();
            System.out.println("All Enterprises: " + entreprises);

            // Step 5: Find Enterprise by Name
            Entreprise foundEntreprise = entrepriseMetier.findByName("Tech Solutions");
            System.out.println("Found Entreprise by Name: " + foundEntreprise);

            // Step 6: Test Update Method
            entreprise.setNom("Updated Tech Solutions");
            entrepriseMetier.update(entreprise);
            System.out.println("Updated Entreprise: " + entreprise);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
