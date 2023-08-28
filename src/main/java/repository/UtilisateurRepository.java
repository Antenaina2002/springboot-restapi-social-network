package repository;

import model.Utilisateur;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepository extends repository<Utilisateur> {

    // Constructeur de la classe UtilisateurRepository qui appelle le constructeur de la classe parente
    public UtilisateurRepository(Connection connection) {
        super(connection);
    }

    // Méthode pour insérer un nouvel utilisateur dans la base de données
    @Override
    public void insert(Utilisateur toInsert) throws SQLException {
        String sql = "INSERT INTO utilisateur(nom, prenom, genre, email, telephone, date_inscription) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            extract(statement, toInsert); // Remplit les paramètres de la requête avec les valeurs de l'objet Utilisateur
            statement.executeUpdate(); // Exécute la requête pour insérer l'utilisateur
        }
    }

    // Méthode pour récupérer tous les utilisateurs de la base de données
    @Override
    public List<Utilisateur> findAll() throws SQLException {
        List<Utilisateur> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Utilisateur user = extractUserFromResultSet(resultSet); // Extrait les données de chaque ligne du ResultSet
                allUsers.add(user); // Ajoute l'utilisateur à la liste
            }
        }
        return allUsers; // Retourne la liste d'utilisateurs
    }

    // Méthode pour mettre à jour les informations d'un utilisateur par son ID
    @Override
    public void updateById(int id, Utilisateur object) throws SQLException {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, genre = ?, " +
                "email = ?, telephone = ?, date_inscription = ? WHERE id_utilisateur = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            extract(statement, object); // Remplit les paramètres de la requête avec les valeurs de l'objet Utilisateur
            statement.setInt(7, id); // Définit le paramètre de l'ID
            statement.executeUpdate(); // Exécute la requête pour mettre à jour l'utilisateur
        }
    }

    // Méthode pour récupérer un utilisateur par son ID
    @Override
    public Optional<Utilisateur> findById(int id) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id); // Définit le paramètre de l'ID
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Utilisateur user = extractUserFromResultSet(resultSet); // Extrait les données de la ligne
                    return Optional.of(user); // Retourne l'utilisateur extrait
                }
            }
        }
        return Optional.empty(); // Retourne un Optional vide si l'utilisateur n'est pas trouvé
    }

    // Méthode pour supprimer un utilisateur par son ID
    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id); // Définit le paramètre de l'ID
            statement.executeUpdate(); // Exécute la requête pour supprimer l'utilisateur
        }
    }

    // Méthode pour extraire les données d'une ligne du ResultSet et créer un objet Utilisateur
    private Utilisateur extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_utilisateur");
        String nom = resultSet.getString("nom");
        String prenom = resultSet.getString("prenom");
        char genre = resultSet.getString("genre").charAt(0);
        String email = resultSet.getString("email");
        String telephone = resultSet.getString("telephone");
        LocalDateTime dateInscription = resultSet.getTimestamp("date_inscription").toLocalDateTime(); // Convertit java.sql.Timestamp en LocalDateTime

        return new Utilisateur(id, nom, prenom, genre, email, telephone, dateInscription);
    }

    // Méthode pour remplir les paramètres de la requête SQL avec les valeurs de l'objet Utilisateur
    private void extract(PreparedStatement statement, Utilisateur object) throws SQLException {
        statement.setString(1, object.getNom());
        statement.setString(2, object.getPrenom());
        statement.setString(3, String.valueOf(object.getGenre()));
        statement.setString(4, object.getEmail());
        statement.setString(5, object.getTelephone());
        statement.setDate(6, java.sql.Date.valueOf(object.getDate_inscription().toLocalDate())); // Convertit LocalDateTime en java.sql.Date
    }
}
