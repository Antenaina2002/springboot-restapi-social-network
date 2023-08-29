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

    // Récupère la liste de tous les utilisateurs
    public List<Utilisateur> afficherTousLesUtilisateurs() {
        try {
            return utilisateurRepository.findAll();
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la récupération des utilisateurs : " + e.getMessage());
            return null; // Retourne null en cas d'erreur
        }
    }

    // Récupère un utilisateur par son ID, avec gestion d'erreur si l'utilisateur n'existe pas
    public Utilisateur afficherUtilisateurParId(int id) {
        try {
            // Utilise Optional pour encapsuler la récupération de l'utilisateur par ID
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);

            // Vérifie si l'utilisateur a été trouvé dans Optional
            if (utilisateur.isPresent()) {
                return utilisateur.get(); // Retourne l'utilisateur trouvé
            } else {
                System.out.println("L'utilisateur n'existe pas.");
                return null; // Retourne null si l'utilisateur n'existe pas
            }
        } catch (SQLException e) {
            // Si une exception SQL est levée (par exemple, une erreur lors de la requête à la base de données)
            System.out.println("Une erreur s'est produite lors de la récupération de l'utilisateur : " + e.getMessage());
            return null; // Retourne null en cas d'erreur
        }
    }


    // Ajoute un nouvel utilisateur
    public void ajouterNouvelUtilisateur(Utilisateur utilisateur) throws SQLException {
        utilisateurRepository.insert(utilisateur);
    }

    // Supprime un utilisateur par son ID, avec gestion d'erreur si l'utilisateur n'existe pas
    public void supprimerUtilisateur(int id) throws SQLException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if (utilisateurOptional.isEmpty()) {
            System.out.println("L'utilisateur n'existe pas.");
        } else {
            utilisateurRepository.deleteById(id);
        }
    }

    // Met à jour un utilisateur par son ID, avec gestion d'erreur si l'utilisateur n'existe pas
    public void mettreAJourUtilisateur(int id, Utilisateur utilisateur) throws SQLException {
        Optional<Utilisateur> utilisateurExistantOptional = utilisateurRepository.findById(id);
        if (utilisateurExistantOptional.isEmpty()) {
            System.out.println("L'utilisateur n'existe pas.");
        } else {
            Utilisateur utilisateurExistant = utilisateurExistantOptional.get();

            // Met à jour les champs non null de l'utilisateur existant
            if (utilisateur.getNom() != null) {
                utilisateurExistant.setNom(utilisateur.getNom());
            }
            if (utilisateur.getPrenom() != null) {
                utilisateurExistant.setPrenom(utilisateur.getPrenom());
            }
            // Répétez ces étapes pour les autres champs à mettre à jour

            // Appel à la méthode de mise à jour du repository avec l'utilisateur mis à jour
            utilisateurRepository.updateById(id, utilisateurExistant);
        }
    }
}
