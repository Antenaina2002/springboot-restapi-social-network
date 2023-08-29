package service;

import model.Poste;
import repository.PosteRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PosteService {
    private final PosteRepository posteRepository;

    public PosteService(PosteRepository posteRepository) {
        this.posteRepository = posteRepository;
    }

    // Créer un nouveau poste
    public void createPoste(Poste poste) throws SQLException {
        try {
            // Appel à la méthode du repository pour insérer le poste
            posteRepository.insert(poste);
        } catch (SQLException e) {
            // Gestion de l'exception en cas d'échec de l'insertion
            throw new SQLException("Erreur lors de la création du poste", e);
        }
    }

    // Mettre à jour un poste par son ID
    public void updatePoste(int id, Poste poste) throws SQLException {
        try {
            // Vérification si le poste existe avant de mettre à jour
            if (getPosteById(id).isPresent()) {
                posteRepository.update(id, poste);
            } else {
                throw new IllegalArgumentException("Poste introuvable pour la mise à jour");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour du poste", e);
        }
    }

    // Supprimer un poste par son ID
    public void deletePoste(int id) throws SQLException {
        try {
            // Vérification si le poste existe avant de supprimer
            if (getPosteById(id).isPresent()) {
                posteRepository.delete(id);
            } else {
                throw new IllegalArgumentException("Poste introuvable pour la suppression");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression du poste", e);
        }
    }

    // Récupérer tous les postes
    public List<Poste> getAllPostes() throws SQLException {
        try {
            return posteRepository.findAll();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération des postes", e);
        }
    }

    // Récupérer un poste par son ID
    public Optional<Poste> getPosteById(int id) throws SQLException {
        try {
            return posteRepository.findById(id);
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération du poste par ID", e);
        }
    }
}
