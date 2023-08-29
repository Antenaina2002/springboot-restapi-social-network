package service;

import model.Commentaire;
import repository.CommentaireRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {
    private final CommentaireRepository commentaireRepository;

    public CommentaireService(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    // Créer un nouveau commentaire
    public void createCommentaire(Commentaire commentaire) throws SQLException {
        try {
            commentaireRepository.insert(commentaire);
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la création du commentaire", e);
        }
    }

    // Mettre à jour un commentaire par son ID
    public void updateCommentaire(int id, Commentaire commentaire) throws SQLException {
        try {
            Optional<Commentaire> existingCommentaire = getCommentaireById(id);
            if (existingCommentaire.isPresent()) {
                commentaireRepository.update(id, commentaire);
            } else {
                throw new CommentaireNotFoundException("Commentaire introuvable pour la mise à jour");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour du commentaire", e);
        }
    }

    // Supprimer un commentaire par son ID
    public void deleteCommentaire(int id) throws SQLException {
        try {
            Optional<Commentaire> existingCommentaire = getCommentaireById(id);
            if (existingCommentaire.isPresent()) {
                commentaireRepository.delete(id);
            } else {
                throw new CommentaireNotFoundException("Commentaire introuvable pour la suppression");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression du commentaire", e);
        }
    }

    // Récupérer tous les commentaires
    public List<Commentaire> getAllCommentaires() throws SQLException {
        try {
            return commentaireRepository.findAll();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération des commentaires", e);
        }
    }

    // Récupérer un commentaire par son ID
    public Optional<Commentaire> getCommentaireById(int id) throws SQLException {
        try {
            return commentaireRepository.findById(id);
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération du commentaire par ID", e);
        }
    }

    // Exception personnalisée pour un commentaire non trouvé
    public static class CommentaireNotFoundException extends RuntimeException {
        public CommentaireNotFoundException(String message) {
            super(message);
        }
    }
}
