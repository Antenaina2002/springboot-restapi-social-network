package repository;

import model.Publication;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PublicationRepository {
    private static DataSource dataSource = null;

    public PublicationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Méthode pour insérer une nouvelle publication dans la base de données
    public void insert(Publication publication) throws SQLException {
        String sql = "INSERT INTO publication (contenu) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, publication.getContenu());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour une publication par son ID
    public void update(int id, Publication publication) throws SQLException {
        String sql = "UPDATE publication SET contenu = ? WHERE id_publication = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, publication.getContenu());
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer une publication par son ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM publication WHERE id_publication = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour trouver toutes les publications
    public static List<Publication> findAll() throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String sql = "SELECT * FROM publication";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                publications.add(extractPublicationFromResultSet(resultSet));
            }
        }
        return publications;
    }

    // Méthode pour trouver une publication par son ID
    public Optional<Publication> findById(int id) throws SQLException {
        String sql = "SELECT * FROM publication WHERE id_publication = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractPublicationFromResultSet(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    // Méthode pour extraire une publication à partir du ResultSet
    private static Publication extractPublicationFromResultSet(ResultSet resultSet) throws SQLException {
        return new Publication(
                resultSet.getInt("id_publication"),
                resultSet.getString("contenu")
        );
    }
}
