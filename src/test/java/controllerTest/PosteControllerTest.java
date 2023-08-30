package controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.PosteController;
import model.Poste;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PosteService;

class PosteControllerTest {

    private PosteService posteService;
    private PosteController posteController;

    @BeforeEach
    void setUp() {
        // Création des mocks pour les services
        posteService = mock(PosteService.class);
        posteController = new PosteController(posteService);
    }

    @Test
    void testListOfAllPosts() throws SQLException {
        // Arrange : Préparation des données de test
        List<Poste> postes = new ArrayList<>();
        postes.add(new Poste(1, Timestamp.valueOf("2023-08-30 12:00:00"), 101, 201));
        postes.add(new Poste(2, Timestamp.valueOf("2023-08-31 10:00:00"), 102, 202));
        // Configuration du comportement du mock
        when(posteService.getAllPostes()).thenReturn(postes);

        // Act : Exécution de la méthode à tester
        List<Poste> response = posteController.listOfAllPosts();

        // Assert : Vérification des résultats
        assertEquals(postes, response);
        System.out.println("testListOfAllPosts réussi");
    }

    @Test
    void testGetPostById_PosteExiste() throws SQLException {
        // Arrange : Préparation des données de test
        int postId = 1;
        Poste poste = new Poste(1, Timestamp.valueOf("2023-08-30 12:00:00"), 101, 201);
        // Configuration du comportement du mock
        when(posteService.getPosteById(postId)).thenReturn(Optional.of(poste));

        // Act : Exécution de la méthode à tester
        Optional<Poste> response = posteController.getPostById(postId);

        // Assert : Vérification des résultats
        assertTrue(response.isPresent());
        assertEquals(poste, response.get());
        System.out.println("testGetPostById_PosteExiste réussi");
    }

    @Test
    void testGetPostById_PosteNExistePas() throws SQLException {
        // Arrange : Préparation des données de test
        int postId = 100;
        // Configuration du comportement du mock
        when(posteService.getPosteById(postId)).thenReturn(Optional.empty());

        // Act : Exécution de la méthode à tester
        Optional<Poste> response = posteController.getPostById(postId);

        // Assert : Vérification des résultats
        assertFalse(response.isPresent());
        System.out.println("testGetPostById_PosteNExistePas réussi");
    }

    @Test
    void testCreateNewPost() throws SQLException {
        // Arrange : Préparation des données de test
        Poste nouveauPoste = new Poste(3, Timestamp.valueOf("2023-08-31 10:30:00"), 103, 203);

        // Act : Exécution de la méthode à tester
        assertDoesNotThrow(() -> posteController.createNewPost(nouveauPoste));
        verify(posteService).createPoste(nouveauPoste);
        System.out.println("testCreateNewPost réussi");
    }

    @Test
    void testUpdatePost() throws SQLException {
        // Arrange : Préparation des données de test
        int postId = 1;
        Poste posteMaj = new Poste(1, Timestamp.valueOf("2023-08-30 12:00:00"), 101, 201);

        // Act : Exécution de la méthode à tester
        assertDoesNotThrow(() -> posteController.updatePost(postId, posteMaj));
        verify(posteService).updatePoste(postId, posteMaj);
        System.out.println("testUpdatePost réussi");
    }

    @Test
    void testDeletePost() throws SQLException {
        // Arrange : Préparation des données de test
        int postId = 1;

        // Act : Exécution de la méthode à tester
        assertDoesNotThrow(() -> posteController.deletePost(postId));
        verify(posteService).deletePoste(postId);
        System.out.println("testDeletePost réussi");
    }
}
