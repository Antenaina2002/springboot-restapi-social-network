package controller;

import model.Utilisateur;
import service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

// @RestController indique que cette classe est un contrôleur REST.
@RestController
// @RequestMapping spécifie le chemin de base pour les routes dans ce contrôleur.
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    // Injection de dépendance pour le service Utilisateur.
    private final UtilisateurService utilisateurService;

    // Constructeur pour l'injection de dépendance.
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // @GetMapping sans argument spécifie une route pour une requête GET à "/utilisateurs".
    @GetMapping
    public ResponseEntity<List<Utilisateur>> afficherTousLesUtilisateurs() {
        // Appel à la méthode du service pour obtenir tous les utilisateurs.
        List<Utilisateur> utilisateurs = utilisateurService.afficherTousLesUtilisateurs();
        // Retourne la liste des utilisateurs avec un statut HTTP 200 (OK).
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    // @GetMapping avec un argument spécifie une route pour une requête GET à "/utilisateurs/{id}".
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> afficherUtilisateurParId(@PathVariable int id) {
        // Appel à la méthode du service pour obtenir un utilisateur par son ID.
        Utilisateur utilisateur = utilisateurService.afficherUtilisateurParId(id);
        // Si l'utilisateur existe, retourne l'utilisateur avec un statut HTTP 200 (OK).
        // Sinon, retourne un statut HTTP 404 (Not Found).
        if (utilisateur != null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @PostMapping spécifie une route pour une requête POST à "/utilisateurs".
    @PostMapping
    public ResponseEntity<Utilisateur> ajouterNouvelUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            // Appel à la méthode du service pour ajouter un nouvel utilisateur.
            utilisateurService.ajouterNouvelUtilisateur(utilisateur);
            // Si l'ajout est réussi, retourne un statut HTTP 201 (Created).
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @DeleteMapping spécifie une route pour une requête DELETE à "/utilisateurs/{id}".
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable int id) {
        try {
            // Appel à la méthode du service pour supprimer un utilisateur par son ID.
            utilisateurService.supprimerUtilisateur(id);
            // Si la suppression est réussie, retourne un statut HTTP 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping spécifie une route pour une requête PUT à "/utilisateurs/{id}".
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> mettreAJourUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateur) {
        try {
            // Appel à la méthode du service pour mettre à jour un utilisateur par son ID.
            utilisateurService.mettreAJourUtilisateur(id, utilisateur);
            // Si la mise à jour est réussie, retourne un statut HTTP 200 (OK).
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e){
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

