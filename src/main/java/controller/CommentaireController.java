package controller;

import model.Commentaire;
import service.CommentaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

// @RestController indique que cette classe est un contrôleur REST.
@RestController
// @RequestMapping spécifie le chemin de base pour les routes dans ce contrôleur.
@RequestMapping("/commentaires")
public class CommentaireController {

    // Injection de dépendance pour le service Commentaire.
    private final CommentaireService commentaireService;

    // Constructeur pour l'injection de dépendance.
    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    // @GetMapping sans argument spécifie une route pour une requête GET à "/commentaires".
    @GetMapping
    public ResponseEntity<List<Commentaire>> getAllCommentaires() {
        try {
            // Appel à la méthode du service pour obtenir tous les commentaires.
            List<Commentaire> commentaires = commentaireService.getAllCommentaires();
            // Retourne la liste des commentaires avec un statut HTTP 200 (OK).
            return new ResponseEntity<>(commentaires, HttpStatus.OK);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping avec un argument spécifie une route pour une requête GET à "/commentaires/{id}".
    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getCommentaireById(@PathVariable int id) {
        try {
            // Appel à la méthode du service pour obtenir un commentaire par son ID.
            Commentaire commentaire = commentaireService.getCommentaireById(id).orElse(null);
            // Si le commentaire existe, retourne le commentaire avec un statut HTTP 200 (OK).
            // Sinon, retourne un statut HTTP 404 (Not Found).
            if (commentaire != null) {
                return new ResponseEntity<>(commentaire, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping spécifie une route pour une requête POST à "/commentaires".
    @PostMapping
    public ResponseEntity<Commentaire> createCommentaire(@RequestBody Commentaire commentaire) {
        try {
            // Appel à la méthode du service pour créer un nouveau commentaire.
            commentaireService.createCommentaire(commentaire);
            // Si la création est réussie, retourne un statut HTTP 201 (Created).
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping spécifie une route pour une requête PUT à "/commentaires/{id}".
    @PutMapping("/{id}")
    public ResponseEntity<Commentaire> updateCommentaire(@PathVariable int id, @RequestBody Commentaire commentaire) {
        try {
            // Appel à la méthode du service pour mettre à jour un commentaire par son ID.
            commentaireService.updateCommentaire(id, commentaire);
            // Si la mise à jour est réussie, retourne un statut HTTP 200 (OK).
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @DeleteMapping spécifie une route pour une requête DELETE à "/commentaires/{id}".
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable int id) {
        try {
            // Appel à la méthode du service pour supprimer un commentaire par son ID.
            commentaireService.deleteCommentaire(id);
            // Si la suppression est réussie, retourne un statut HTTP 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
