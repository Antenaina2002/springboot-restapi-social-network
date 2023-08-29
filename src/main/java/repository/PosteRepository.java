package repository;

import model.Poste;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PosteRepository {
    private final DataSource dataSource;

    public PosteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Méthode pour insérer un nouveau poste
    public void insert(Poste poste) throws SQLException {
        String sql = "INSERT INTO poste (date_poste, auteur_id, publication_id) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, poste.getDate_poste()); // Utiliser setTimestamp directement
            statement.setInt(2, poste.getAuteurId());
            statement.setInt(3, poste.getPublicationId());

            statement.executeUpdate();
        }
    }



    // Méthode pour mettre à jour un poste par son ID
    public void update(int id, Poste poste) throws SQLException {
        String sql = "UPDATE poste SET date_poste = ?, auteur_id = ?, publication_id = ? WHERE id_poste = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, poste.getDate_poste());
            statement.setInt(2, poste.getAuteurId());
            statement.setInt(3, poste.getPublicationId());
            statement.setInt(4, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer un poste par son ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM poste WHERE id_poste = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour trouver tous les postes
    public List<Poste> findAll() throws SQLException {
        List<Poste> postes = new ArrayList<>();
        String sql = "SELECT * FROM poste";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                postes.add(extractPosteFromResultSet(resultSet));
            }
        }
        return postes;
    }

    // Méthode pour trouver un poste par son ID
    public Optional<Poste> findById(int id) throws SQLException {
        String sql = "SELECT * FROM poste WHERE id_poste = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractPosteFromResultSet(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    // Méthode pour extraire un poste à partir du ResultSet
    private Poste extractPosteFromResultSet(ResultSet resultSet) throws SQLException {
        return new Poste(
                resultSet.getInt("id_poste"),
                resultSet.getTimestamp("date_poste"),
                resultSet.getInt("auteur_id"),
                resultSet.getInt("publication_id")
        );
    }
}
