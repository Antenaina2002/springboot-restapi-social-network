package repository;

import model.Messages;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {

    private final Connection connection;

    // Constructeur qui reçoit la connexion à la base de données
    public MessageRepository(Connection connection) {
        this.connection = connection;
    }

    // Récupère tous les messages entre deux utilisateurs spécifiques
    public List<Messages> recupererTousLesMessagesEntreUtilisateurs(int envoyeurId, int receveurId) throws SQLException {
        List<Messages> tousLesMessagesEntreUtilisateurs = new ArrayList<>();
        // Requête SQL pour récupérer les messages entre les utilisateurs
        String sql = """
                SELECT * FROM messages WHERE
                (envoyeur_id = ? AND receveur_id = ?)
                OR
                (envoyeur_id = ? AND receveur_id = ?)
                ORDER BY message_date ASC
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Remplissage des paramètres dans la requête
            statement.setInt(1, envoyeurId);
            statement.setInt(2, receveurId);
            statement.setInt(3, receveurId);
            statement.setInt(4, envoyeurId);
            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Extraction des données du résultat
                Timestamp dateMessage = resultSet.getTimestamp("message_date");
                String texteMessage = resultSet.getString("message_text");
                // Récupération de l'ID du message à partir du résultat
                int idMessage = resultSet.getInt("id_message");
                // Création d'un objet Message et ajout à la liste
                Messages message = new Messages(
                        idMessage,
                        envoyeurId,
                        receveurId,
                        texteMessage,
                        dateMessage.toLocalDateTime()
                );
                tousLesMessagesEntreUtilisateurs.add(message);
            }
        }
        // Retourne la liste de tous les messages entre les utilisateurs
        return tousLesMessagesEntreUtilisateurs;
    }

    // Insère un nouveau message dans la table des messages
    public void insererNouveauMessage(Messages message) throws SQLException {
        // Requête SQL pour insérer un nouveau message
        String sql = "INSERT INTO messages(envoyeur_id, receveur_id, message_text) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Remplissage des paramètres dans la requête
            statement.setInt(1, message.getEnvoyeur_id());
            statement.setInt(2, message.getReceveur_id());
            statement.setString(3, message.getMessage_text());
            // Exécution de la requête
            statement.executeUpdate();
        }
    }

    // Met à jour les messages comme vus pour les deux utilisateurs spécifiés
    public void updateMessages(int envoyeurId, int receveurId) throws SQLException {
        // Requête SQL pour mettre à jour les messages comme vus
        String sql = """
                UPDATE messages
                SET message_date = message_date
                WHERE (envoyeur_id = ? AND receveur_id = ?)
                      OR (envoyeur_id = ? AND receveur_id = ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Remplissage des paramètres dans la requête
            statement.setInt(1, envoyeurId);
            statement.setInt(2, receveurId);
            statement.setInt(3, receveurId);
            statement.setInt(4, envoyeurId);
            // Exécution de la requête
            statement.executeUpdate();
        }
    }

    // Supprime les messages associés à un utilisateur spécifique
    public void suppressionMessage(int idUtilisateur) throws SQLException {
        // Requête SQL pour supprimer les messages associés à un utilisateur
        String sql = "DELETE FROM messages WHERE envoyeur_id = ? OR receveur_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Remplissage des paramètres dans la requête
            statement.setInt(1, idUtilisateur);
            statement.setInt(2, idUtilisateur);
            // Exécution de la requête
            statement.executeUpdate();
        }
    }
}
