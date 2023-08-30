package controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
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
        publicationService = mock(PublicationService.class);
        publicationController = new PublicationController(publicationService);
    }

    @Test
    void testAfficherPublicationParId_PublicationExiste() throws SQLException {
        int publicationId = 1;
        Publication publication = new Publication(publicationId, "Nom1");
        when(publicationService.getPublicationById(publicationId)).thenReturn(Optional.of(publication));

        ResponseEntity<Publication> response = publicationController.getPublicationById(publicationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(publication, response.getBody());
        System.out.println("testAfficherPublicationParId_PublicationExiste réussi");
    }

    @Test
    void testAjouterNouvellePublication_AjoutAvecSucces() throws SQLException {
        Publication nouvellePublication = new Publication(3, "Nom1");

        ResponseEntity<Publication> response = publicationController.createPublication(nouvellePublication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("testAjouterNouvellePublication_AjoutAvecSucces réussi");
    }

    @Test
    void testAjouterNouvellePublication_ErreurSQL() throws SQLException {
        Publication nouvellePublication = new Publication(3, "Nom1");
        doThrow(new SQLException()).when(publicationService).createPublication(nouvellePublication);

        ResponseEntity<Publication> response = publicationController.createPublication(nouvellePublication);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testAjouterNouvellePublication_ErreurSQL réussi");
    }

    @Test
    void testSupprimerPublication_SuppressionAvecSucces() throws SQLException {
        int publicationId = 1;

        ResponseEntity<Void> response = publicationController.deletePublication(publicationId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("testSupprimerPublication_SuppressionAvecSucces réussi");
    }

    @Test
    void testSupprimerPublication_ErreurSQL() throws SQLException {
        int publicationId = 1;
        doThrow(new SQLException()).when(publicationService).deletePublication(publicationId);

        ResponseEntity<Void> response = publicationController.deletePublication(publicationId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testSupprimerPublication_ErreurSQL réussi");
    }

    @Test
    void testMettreAJourUtilisateur_MiseAJourAvecSucces() throws SQLException {
        int publicationId = 1;
        Publication publicationMaj = new Publication(publicationId, "Nom1");

        ResponseEntity<Publication> response = publicationController.updatePublication(publicationId, publicationMaj);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("testMettreAJourPublication_MiseAJourAvecSucces réussi");
    }

    @Test
    void testMettreAJourUtilisateur_ErreurSQL() throws SQLException {
        int publicationId = 1;
        Publication publicationMaj = new Publication(publicationId, "Nom1");
        doThrow(new SQLException()).when(publicationService).updatePublication(publicationId, publicationMaj);

        ResponseEntity<Publication> response = publicationController.updatePublication(publicationId, publicationMaj);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("testMettreAJourPublication_ErreurSQL réussi");
    }

}
