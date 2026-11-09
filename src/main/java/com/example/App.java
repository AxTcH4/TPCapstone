package com.example;

/**
 * Hello world!
 *
 */

import com.example.model.*;
import com.example.util.DataInitializer;
import com.example.util.MigrationExecutor;
import com.example.util.PerformanceReport;

import javax.persistence.*;

public class App {

//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-reservations-pu");
//        EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//
//        Utilisateur user = new Utilisateur();
//        user.setEmail("alice@example.com");
//        user.setNom("Alice Dupont");
//
//        Salle salle = new Salle();
//        salle.setNom("Salle A");
//
//        Equipement projecteur = new Equipement();
//        projecteur.setNom("Projecteur");
//
//        salle.addEquipement(projecteur);
//
//        Reservation reservation = new Reservation();
//        reservation.setSalle(salle);
//        reservation.setUtilisateur(user);
//
//        user.addReservation(reservation);
//
//        em.persist(user); // cascade persist propagée
//
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        Utilisateur userr = em.find(Utilisateur.class, 1L);
//        Reservation res = userr.getReservations().iterator().next();
//        user.getReservations().remove(res);
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        Utilisateur userrr = em.find(Utilisateur.class, 1L);
//        em.remove(userrr);
//        em.getTransaction().commit();
//
//        em.close();
//        emf.close();
//    }

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReservationPU");
        EntityManager em = emf.createEntityManager();

        // Exécuter la migration
        MigrationExecutor migrationExecutor = new MigrationExecutor(
                "jdbc:mysql://localhost:3306/reservation",
                "root",
                "password"
        );
        migrationExecutor.executeMigration("db/migration/v2_0_add_description.sql");

        // Initialiser les données
        DataInitializer initializer = new DataInitializer(em);
        initializer.initData();

        // Générer rapport de performance
        PerformanceReport report = new PerformanceReport(em);
        report.printReport();

        em.close();
        emf.close();
    }
}