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
        utilisateurService = mock(UtilisateurService.class);
        utilisateurController = new UtilisateurController(utilisateurService);
    }

    @Test
    void testAfficherTousLesUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs.add(new Utilisateur(1, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now()));
        utilisateurs.add(new Utilisateur(2, "Nom2", "Prenom2", 'F', "email2@test.com", "0987654321", LocalDateTime.now()));

        when(utilisateurService.afficherTousLesUtilisateurs()).thenReturn(utilisateurs);

        ResponseEntity<List<Utilisateur>> response = utilisateurController.afficherTousLesUtilisateurs();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utilisateurs, response.getBody());
        System.out.println("testAfficherTousLesUtilisateurs réussi");
    }

    @Test
    void testAfficherUtilisateurParId_UtilisateurExiste() {

        int userId = 1;
        Utilisateur utilisateur = new Utilisateur(userId, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        when(utilisateurService.afficherUtilisateurParId(userId)).thenReturn(utilisateur);

        ResponseEntity<Utilisateur> response = utilisateurController.afficherUtilisateurParId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utilisateur, response.getBody());
        System.out.println("testAfficherUtilisateurParId_UtilisateurExiste réussi");
    }

    @Test
    void testAfficherUtilisateurParId_UtilisateurNExistePas() {
        int utilisateurId = 100;
        when(utilisateurService.afficherUtilisateurParId(utilisateurId)).thenReturn(null);

        ResponseEntity<Utilisateur> response = utilisateurController.afficherUtilisateurParId(utilisateurId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("testAfficherUtilisateurParId_UtilisateurNExistePas réussi");
    }

    @Test
    void testAjouterNouvelUtilisateur_AjoutAvecSucces() throws SQLException {
        Utilisateur nouvelUtilisateur = new Utilisateur(3, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        ResponseEntity<Utilisateur> response = utilisateurController.ajouterNouvelUtilisateur(nouvelUtilisateur);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("testAjouterNouvelUtilisateur_AjoutAvecSucces réussi");
    }

    @Test
    void testAjouterNouvelUtilisateur_ErreurSQL() throws SQLException {
        Utilisateur nouvelUtilisateur = new Utilisateur(3, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        doThrow(new SQLException()).when(utilisateurService).ajouterNouvelUtilisateur(nouvelUtilisateur);

        ResponseEntity<Utilisateur> response = utilisateurController.ajouterNouvelUtilisateur(nouvelUtilisateur);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testAjouterNouvelUtilisateur_ErreurSQL réussi");
    }

    @Test
    void testSupprimerUtilisateur_SuppressionAvecSucces(){
        int utilisateurId = 1;

        ResponseEntity<Void> response = utilisateurController.supprimerUtilisateur(utilisateurId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("testSupprimerUtilisateur_SuppressionAvecSucces réussi");
    }

    @Test
    void testSupprimerUtilisateur_ErreurSQL() throws SQLException {
        int utilisateurId = 1;
        doThrow(new SQLException()).when(utilisateurService).supprimerUtilisateur(utilisateurId);

        ResponseEntity<Void> response = utilisateurController.supprimerUtilisateur(utilisateurId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testSupprimerUtilisateur_ErreurSQL réussi");
    }

    @Test
    void testMettreAJourUtilisateur_MiseAJourAvecSucces() {
        int userId = 1;
        Utilisateur utilisateurMaj = new Utilisateur(userId, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        ResponseEntity<Utilisateur> response = utilisateurController.mettreAJourUtilisateur(userId, utilisateurMaj);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("testMettreAJourUtilisateur_MiseAJourAvecSucces réussi");
    }

    @Test
    void testMettreAJourUtilisateur_ErreurSQL() throws SQLException {
        int utilisateurId = 1;
        Utilisateur utilisateurMaj = new Utilisateur(utilisateurId, "Nom1", "Prenom1", 'M', "email1@test.com", "1234567890", LocalDateTime.now());

        doThrow(new SQLException()).when(utilisateurService).mettreAJourUtilisateur(utilisateurId, utilisateurMaj);

        ResponseEntity<Utilisateur> response = utilisateurController.mettreAJourUtilisateur(utilisateurId, utilisateurMaj);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testMettreAJourUtilisateur_ErreurSQL réussi");
    }

}
