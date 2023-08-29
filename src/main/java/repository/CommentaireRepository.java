package repository;

import model.Commentaire;
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
public class CommentaireRepository {
    private final DataSource dataSource;

    public CommentaireRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Méthode pour insérer un nouveau commentaire dans la base de données
    public void insert(Commentaire commentaire) throws SQLException {
        String sql = "INSERT INTO commentaire (commentaire, commentaire_date, commentateur_id, publication_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, commentaire.getCommentaire());
            statement.setTimestamp(2, commentaire.getCommentaire_date());
            statement.setInt(3, commentaire.getCommentateurId());
            statement.setInt(4, commentaire.getPublicationId());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour un commentaire par son ID
    public void update(int id, Commentaire commentaire) throws SQLException {
        String sql = "UPDATE commentaire SET commentaire = ?, commentaire_date = ?, commentateur_id = ?, publication_id = ? WHERE id_commentaire = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, commentaire.getCommentaire());
            statement.setTimestamp(2, commentaire.getCommentaire_date());
            statement.setInt(3, commentaire.getCommentateurId());
            statement.setInt(4, commentaire.getPublicationId());
            statement.setInt(5, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer un commentaire par son ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM commentaire WHERE id_commentaire = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour trouver tous les commentaires
    public List<Commentaire> findAll() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaire";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                commentaires.add(extractCommentaireFromResultSet(resultSet));
            }
        }
        return commentaires;
    }

    // Méthode pour trouver un commentaire par son ID
    public Optional<Commentaire> findById(int id) throws SQLException {
        String sql = "SELECT * FROM commentaire WHERE id_commentaire = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractCommentaireFromResultSet(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    // Méthode pour extraire un commentaire à partir du ResultSet
    private Commentaire extractCommentaireFromResultSet(ResultSet resultSet) throws SQLException {
        return new Commentaire(
                resultSet.getInt("id_commentaire"),
                resultSet.getString("commentaire"),
                resultSet.getTimestamp("commentaire_date"),
                resultSet.getInt("commentateur_id"),
                resultSet.getInt("publication_id")
        );
    }
}
