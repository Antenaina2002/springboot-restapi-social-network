package service;

import model.Messages;
import repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Récupère tous les messages entre deux utilisateurs
    public List<Messages> recupererTousLesMessagesEntreUtilisateurs(int envoyeurId, int receveurId) {
        try {
            return messageRepository.recupererTousLesMessagesEntreUtilisateurs(envoyeurId, receveurId);
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la récupération des messages : " + e.getMessage());
            return null; // Retourne null en cas d'erreur
        }
    }

    // Ajoute un nouveau message
    public void insererNouveauMessage(Messages message) {
        try {
            messageRepository.insererNouveauMessage(message);
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de l'insertion du message : " + e.getMessage());
        }
    }

    // Marque tous les messages entre deux utilisateurs comme lus
    public void marquerMessagesCommeLus(int envoyeurId, int receveurId) {
        try {
            messageRepository.updateMessages(envoyeurId, receveurId);
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la mise à jour des messages : " + e.getMessage());
        }
    }

    // Supprime tous les messages d'un utilisateur
    public void supprimerMessagesUtilisateur(int userId) {
        try {
            messageRepository.suppressionMessage(userId);
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la suppression des messages : " + e.getMessage());
        }
    }

    // Ajoutez ici d'autres méthodes pour la gestion des messages, si nécessaire
}
