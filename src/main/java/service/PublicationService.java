package service;

import model.Publication;
import repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {
    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    // Créer une nouvelle publication
    public void createPublication(Publication publication) throws SQLException {
        try {
            publicationRepository.insert(publication);
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la création de la publication", e);
        }
    }

    // Mettre à jour une publication par son ID
    public void updatePublication(int id, Publication publication) throws SQLException {
        try {
            if (getPublicationById(id).isPresent()) {
                publicationRepository.update(id, publication);
            } else {
                throw new PublicationNotFoundException("Publication introuvable pour la mise à jour");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour de la publication", e);
        }
    }

    // Supprimer une publication par son ID
    public void deletePublication(int id) throws SQLException {
        try {
            if (getPublicationById(id).isPresent()) {
                publicationRepository.delete(id);
            } else {
                throw new PublicationNotFoundException("Publication introuvable pour la suppression");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression de la publication", e);
        }
    }

    // Récupérer toutes les publications
    public List<Publication> getAllPublications() throws SQLException {
        try {
            return publicationRepository.findAll();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération des publications", e);
        }
    }

    // Récupérer une publication par son ID
    public Optional<Publication> getPublicationById(int id) throws SQLException {
        try {
            return publicationRepository.findById(id);
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération de la publication par ID", e);
        }
    }

    // Exception personnalisée pour une publication non trouvée
    public static class PublicationNotFoundException extends RuntimeException {
        public PublicationNotFoundException(String message) {
            super(message);
        }
    }
}
