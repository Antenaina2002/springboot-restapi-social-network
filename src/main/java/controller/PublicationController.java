package controller;

import model.Publication;
import service.PublicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

// @RestController indique que cette classe est un contrôleur REST.
@RestController
// @RequestMapping spécifie le chemin de base pour les routes dans ce contrôleur.
@RequestMapping("/publications")
public class PublicationController {

    // Injection de dépendance pour le service Publication.
    private final PublicationService publicationService;

    // Constructeur pour l'injection de dépendance.
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    // @GetMapping sans argument spécifie une route pour une requête GET à "/publications".
    @GetMapping
    public ResponseEntity<List<Publication>> getAllPublications() {
        try {
            // Appel à la méthode du service pour obtenir toutes les publications.
            List<Publication> publications = publicationService.getAllPublications();
            // Retourne la liste des publications avec un statut HTTP 200 (OK).
            return new ResponseEntity<>(publications, HttpStatus.OK);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping avec un argument spécifie une route pour une requête GET à "/publications/{id}".
    @GetMapping("/{id}")
    public ResponseEntity<Publication> getPublicationById(@PathVariable int id) {
        try {
            // Appel à la méthode du service pour obtenir une publication par son ID.
            Publication publication;
            publication = publicationService.getPublicationById(id).orElse(null);
            // Si la publication existe, retourne la publication avec un statut HTTP 200 (OK).
            // Sinon, retourne un statut HTTP 404 (Not Found).
            if (publication != null) {
                return new ResponseEntity<>(publication, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping spécifie une route pour une requête POST à "/publications".
    @PostMapping
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) {
        try {
            // Appel à la méthode du service pour créer une nouvelle publication.
            publicationService.createPublication(publication);
            // Si la création est réussie, retourne un statut HTTP 201 (Created).
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping spécifie une route pour une requête PUT à "/publications/{id}".
    @PutMapping("/{id}")
    public ResponseEntity<Publication> updatePublication(@PathVariable int id, @RequestBody Publication publication) {
        try {
            // Appel à la méthode du service pour mettre à jour une publication par son ID.
            publicationService.updatePublication(id, publication);
            // Si la mise à jour est réussie, retourne un statut HTTP 200 (OK).
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @DeleteMapping spécifie une route pour une requête DELETE à "/publications/{id}".
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable int id) {
        try {
            // Appel à la méthode du service pour supprimer une publication par son ID.
            publicationService.deletePublication(id);
            // Si la suppression est réussie, retourne un statut HTTP 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            // Si une exception SQL est levée, retourne un statut HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
