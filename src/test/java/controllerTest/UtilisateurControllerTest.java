package controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.UtilisateurController;
import model.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.UtilisateurService;

class UtilisateurControllerTest {

    private UtilisateurService utilisateurService;
    private UtilisateurController utilisateurController;

    @BeforeEach
    void setUp() {
        // Création des mocks pour les services
        utilisateurService = mock(UtilisateurService.class);
        utilisateurController = new UtilisateurController(utilisateurService);
    }

    @Test
    void testAfficherTousLesUtilisateurs() {
        // Arrange : Préparation des données de test
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs.add(new Utilisateur(1, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now()));
        utilisateurs.add(new Utilisateur(2, "Nom2", "Prenom2", 'F', "email2@test.com", "0987654321", LocalDateTime.now()));
        // Configuration du comportement du mock
        when(utilisateurService.afficherTousLesUtilisateurs()).thenReturn(utilisateurs);

        // Act : Exécution de la méthode à tester
        ResponseEntity<List<Utilisateur>> response = utilisateurController.afficherTousLesUtilisateurs();

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utilisateurs, response.getBody());
        System.out.println("testAfficherTousLesUtilisateurs réussi");
    }

    @Test
    void testAfficherUtilisateurParId_UtilisateurExiste() {
        // Arrange : Préparation des données de test
        int userId = 1;
        Utilisateur utilisateur = new Utilisateur(userId, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());
        // Configuration du comportement du mock
        when(utilisateurService.afficherUtilisateurParId(userId)).thenReturn(utilisateur);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Utilisateur> response = utilisateurController.afficherUtilisateurParId(userId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utilisateur, response.getBody());
        System.out.println("testAfficherUtilisateurParId_UtilisateurExiste réussi");
    }

    @Test
    void testAfficherUtilisateurParId_UtilisateurNExistePas() {
        // Arrange : Préparation des données de test
        int userId = 100;
        // Configuration du comportement du mock
        when(utilisateurService.afficherUtilisateurParId(userId)).thenReturn(null);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Utilisateur> response = utilisateurController.afficherUtilisateurParId(userId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("testAfficherUtilisateurParId_UtilisateurNExistePas réussi");
    }

    @Test
    void testAjouterNouvelUtilisateur_AjoutAvecSucces() throws SQLException {
        // Arrange : Préparation des données de test
        Utilisateur nouvelUtilisateur = new Utilisateur(3, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        // Act : Exécution de la méthode à tester
        ResponseEntity<Utilisateur> response = utilisateurController.ajouterNouvelUtilisateur(nouvelUtilisateur);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("testAjouterNouvelUtilisateur_AjoutAvecSucces réussi");
    }

    @Test
    void testAjouterNouvelUtilisateur_ErreurSQL() throws SQLException {
        // Arrange : Préparation des données de test
        Utilisateur nouvelUtilisateur = new Utilisateur(3, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());
        // Configuration du comportement du mock pour simuler une exception
        doThrow(new SQLException()).when(utilisateurService).ajouterNouvelUtilisateur(nouvelUtilisateur);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Utilisateur> response = utilisateurController.ajouterNouvelUtilisateur(nouvelUtilisateur);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testAjouterNouvelUtilisateur_ErreurSQL réussi");
    }

    @Test
    void testSupprimerUtilisateur_SuppressionAvecSucces() throws SQLException {
        // Arrange : Préparation des données de test
        int userId = 1;

        // Act : Exécution de la méthode à tester
        ResponseEntity<Void> response = utilisateurController.supprimerUtilisateur(userId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("testSupprimerUtilisateur_SuppressionAvecSucces réussi");
    }

    @Test
    void testSupprimerUtilisateur_ErreurSQL() throws SQLException {
        // Arrange : Préparation des données de test
        int userId = 1;
        // Configuration du comportement du mock pour simuler une exception
        doThrow(new SQLException()).when(utilisateurService).supprimerUtilisateur(userId);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Void> response = utilisateurController.supprimerUtilisateur(userId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testSupprimerUtilisateur_ErreurSQL réussi");
    }

    @Test
    void testMettreAJourUtilisateur_MiseAJourAvecSucces() throws SQLException {
        // Arrange : Préparation des données de test
        int userId = 1;
        Utilisateur utilisateurMaj = new Utilisateur(userId, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        // Act : Exécution de la méthode à tester
        ResponseEntity<Utilisateur> response = utilisateurController.mettreAJourUtilisateur(userId, utilisateurMaj);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("testMettreAJourUtilisateur_MiseAJourAvecSucces réussi");
    }

    @Test
    void testMettreAJourUtilisateur_ErreurSQL() throws SQLException {
        // Arrange : Préparation des données de test
        int userId = 1;
        Utilisateur utilisateurMaj = new Utilisateur(userId, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());
        // Configuration du comportement du mock pour simuler une exception
        doThrow(new SQLException()).when(utilisateurService).mettreAJourUtilisateur(userId, utilisateurMaj);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Utilisateur> response = utilisateurController.mettreAJourUtilisateur(userId, utilisateurMaj);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testMettreAJourUtilisateur_ErreurSQL réussi");
    }

}
