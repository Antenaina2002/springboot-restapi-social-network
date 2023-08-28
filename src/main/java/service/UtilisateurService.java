package service;

import model.Utilisateur;
import repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // Méthode pour ajouter un nouvel utilisateur
    public void addUtilisateur(Utilisateur utilisateur) throws SQLException {
        utilisateurRepository.insert(utilisateur);
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() throws SQLException {
        return utilisateurRepository.findAll();
    }

    // Méthode pour récupérer un utilisateur par son ID
    public Optional<Utilisateur> getUtilisateurById(int id) throws SQLException {
        return utilisateurRepository.findById(id);
    }

    // Méthode pour mettre à jour un utilisateur par son ID
    public void updateUtilisateur(int id, Utilisateur utilisateur) throws SQLException {
        utilisateurRepository.updateById(id, utilisateur);
    }

    // Méthode pour supprimer un utilisateur par son ID
    public void deleteUtilisateur(int id) throws SQLException {
        utilisateurRepository.deleteById(id);
    }
}
