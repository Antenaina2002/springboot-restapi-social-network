package repostiroyTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UtilisateurRepository;
import model.Utilisateur;

class UtilisateurRepositoryTest {

    @Mock
    private Connection connection;

    private UtilisateurRepository utilisateurRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateurRepository = new UtilisateurRepository(connection);
    }

    @Test
    void testInsert() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        Utilisateur utilisateur = new Utilisateur(1, "Nom", "Prenom", 'M', "email@test.com", "1234567890", LocalDateTime.now());

        utilisateurRepository.insert(utilisateur);

        verify(preparedStatement).executeUpdate();
        System.out.println("testInsert réussi");
    }

    @Test
    void testUpdateById() throws SQLException {
        int userId = 1;
        Utilisateur utilisateur = new Utilisateur(userId, "Nom modifié", "Prenom modifié", 'F', "email@test.com", "9876543210", LocalDateTime.now());
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        utilisateurRepository.updateById(userId, utilisateur);

        verify(preparedStatement).executeUpdate();
        System.out.println("testUpdateById réussi");
    }

    @Test
    void testFindById_UserNotExists() throws SQLException {
        int userId = 1;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simuler l'absence de résultat

        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);

        assertFalse(utilisateur.isPresent());
        System.out.println("testFindById_UserNotExists réussi");
    }

    @Test
    void testDeleteById() throws SQLException {
        int userId = 1;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        utilisateurRepository.deleteById(userId);

        verify(preparedStatement).executeUpdate();
        System.out.println("testDeleteById réussi");
    }
}
