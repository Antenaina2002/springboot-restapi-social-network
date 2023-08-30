package controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.PublicationController;
import model.Publication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.PublicationService;

class PublicationControllerTest {

    private PublicationService publicationService;
    private PublicationController publicationController;

    @BeforeEach
    void setUp() {
        // Création des mocks pour les services
        publicationService = mock(PublicationService.class);
        publicationController = new PublicationController(publicationService);
    }

    @Test
    void testAfficherPublicationParId_PublicationExiste() throws SQLException {
        // Arrange : Préparation des données de test
        int publicationId = 1;
        Publication publication = new Publication(publicationId, "Nom1");
        // Configuration du comportement du mock
        when(publicationService.getPublicationById(publicationId)).thenReturn(Optional.of(publication));

        // Act : Exécution de la méthode à tester
        ResponseEntity<Publication> response = publicationController.getPublicationById(publicationId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(publication, response.getBody());
        System.out.println("testAfficherPublicationParId_PublicationExiste réussi");
    }

    @Test
    void testAjouterNouvellePublication_AjoutAvecSucces() throws SQLException {
        // Arrange : Préparation des données de test
        Publication nouvellePublication = new Publication(3, "Nom1");

        // Act : Exécution de la méthode à tester
        ResponseEntity<Publication> response = publicationController.createPublication(nouvellePublication);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("testAjouterNouvellePublication_AjoutAvecSucces réussi");
    }

    @Test
    void testAjouterNouvellePublication_ErreurSQL() throws SQLException {
        // Arrange : Préparation des données de test
        Publication nouvellePublication = new Publication(3, "Nom1");
        // Configuration du comportement du mock pour simuler une exception
        doThrow(new SQLException()).when(publicationService).createPublication(nouvellePublication);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Publication> response = publicationController.createPublication(nouvellePublication);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testAjouterNouvellePublication_ErreurSQL réussi");
    }

    @Test
    void testSupprimerPublication_SuppressionAvecSucces() throws SQLException {
        // Arrange : Préparation des données de test
        int publcationId = 1;

        // Act : Exécution de la méthode à tester
        ResponseEntity<Void> response = publicationController.deletePublication(publcationId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("testSupprimerPublication_SuppressionAvecSucces réussi");
    }

    @Test
    void testSupprimerPublication_ErreurSQL() throws SQLException {
        // Arrange : Préparation des données de test
        int publicationId = 1;
        // Configuration du comportement du mock pour simuler une exception
        doThrow(new SQLException()).when(publicationService).deletePublication(publicationId);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Void> response = publicationController.deletePublication(publicationId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testSupprimerPublication_ErreurSQL réussi");
    }

    @Test
    void testMettreAJourUtilisateur_MiseAJourAvecSucces() throws SQLException {
        // Arrange : Préparation des données de test
        int publicationId = 1;
        Publication publicationMaj = new Publication(publicationId, "Nom1");

        // Act : Exécution de la méthode à tester
        ResponseEntity<Publication> response = publicationController.updatePublication(publicationId, publicationMaj);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("testMettreAJourPublication_MiseAJourAvecSucces réussi");
    }

    @Test
    void testMettreAJourUtilisateur_ErreurSQL() throws SQLException {
        // Arrange : Préparation des données de test
        int publicationId = 1;
        Publication publicationMaj = new Publication(publicationId, "Nom1");
        // Configuration du comportement du mock pour simuler une exception
        doThrow(new SQLException()).when(publicationService).updatePublication(publicationId, publicationMaj);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Publication> response = publicationController.updatePublication(publicationId, publicationMaj);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testMettreAJourPublication_ErreurSQL réussi");
    }

}
