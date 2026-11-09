package com.example;

import com.example.model.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ReservationTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("ReservationPU");
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) em.close();
    }

    @AfterAll
    public static void close() {
        if (emf != null) emf.close();
    }

    @Test
    public void testCreateReservation() {
        em.getTransaction().begin();

        Utilisateur user = new Utilisateur();
        user.setNom("Test User");
        user.setEmail("test.user@example.com");
        em.persist(user);

        Salle salle = em.find(Salle.class, 1L); // Supposons qu'elle existe

        Reservation reservation = new Reservation();
        reservation.setUtilisateur(user);
        reservation.setSalle(salle);
        reservation.setDate("2026-09-11");
        reservation.setHeure("20:50");

        em.persist(reservation);
        em.getTransaction().commit();

        Assertions.assertNotNull(reservation.getId());
    }
}