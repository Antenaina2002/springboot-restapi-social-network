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
        posteService = mock(PosteService.class);
        posteController = new PosteController(posteService);
    }

    @Test
    void testListOfAllPosts() throws SQLException {
        List<Poste> postes = new ArrayList<>();
        postes.add(new Poste(1, Timestamp.valueOf("2023-08-30 12:00:00"), 101, 201));
        postes.add(new Poste(2, Timestamp.valueOf("2023-08-31 10:00:00"), 102, 202));
        when(posteService.getAllPostes()).thenReturn(postes);

        List<Poste> response = posteController.listOfAllPosts();

        assertEquals(postes, response);
        System.out.println("testListOfAllPosts réussi");
    }

    @Test
    void testGetPostById_PosteExiste() throws SQLException {
        int postId = 1;
        Poste poste = new Poste(1, Timestamp.valueOf("2023-08-30 12:00:00"), 101, 201);
        when(posteService.getPosteById(postId)).thenReturn(Optional.of(poste));

        Optional<Poste> response = posteController.getPostById(postId);

        assertTrue(response.isPresent());
        assertEquals(poste, response.get());
        System.out.println("testGetPostById_PosteExiste réussi");
    }

    @Test
    void testGetPostById_PosteNExistePas() throws SQLException {
        int postId = 100;
        when(posteService.getPosteById(postId)).thenReturn(Optional.empty());

        Optional<Poste> response = posteController.getPostById(postId);

        assertFalse(response.isPresent());
        System.out.println("testGetPostById_PosteNExistePas réussi");
    }

    @Test
    void testCreateNewPost() throws SQLException {
        Poste nouveauPoste = new Poste(3, Timestamp.valueOf("2023-08-31 10:30:00"), 103, 203);

        assertDoesNotThrow(() -> posteController.createNewPost(nouveauPoste));
        verify(posteService).createPoste(nouveauPoste);
        System.out.println("testCreateNewPost réussi");
    }

    @Test
    void testUpdatePost() throws SQLException {
        int postId = 1;
        Poste posteMaj = new Poste(1, Timestamp.valueOf("2023-08-30 12:00:00"), 101, 201);

        assertDoesNotThrow(() -> posteController.updatePost(postId, posteMaj));
        verify(posteService).updatePoste(postId, posteMaj);
        System.out.println("testUpdatePost réussi");
    }

    @Test
    void testDeletePost() throws SQLException {
        int postId = 1;

        assertDoesNotThrow(() -> posteController.deletePost(postId));
        verify(posteService).deletePoste(postId);
        System.out.println("testDeletePost réussi");
    }
}
