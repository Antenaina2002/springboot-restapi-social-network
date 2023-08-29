package controller;

import model.Messages;
import service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/message")
public class MessageController {
    private final MessageService messageService;

    // Récupérer tous les messages entre deux utilisateurs
    @GetMapping("/between")
    public ResponseEntity<List<Messages>> displayMessages(@RequestParam int idSender, @RequestParam int idReceiver) {
        try {
            // Appelle le service pour récupérer les messages entre les deux utilisateurs
            List<Messages> messages = messageService.recupererTousLesMessagesEntreUtilisateurs(idSender, idReceiver);
            // Retourne une réponse avec les messages et le code de statut 200 (OK)
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            // En cas d'erreur, logue l'erreur et retourne une réponse avec le code de statut 500 (INTERNAL_SERVER_ERROR)
            System.err.println("Erreur lors de la récupération des messages : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Envoyer un nouveau message
    @PostMapping("/new")
    public ResponseEntity<Void> sendNewMessage(@RequestBody Messages message) {
        try {
            // Appelle le service pour envoyer un nouveau message
            messageService.insererNouveauMessage(message);
            // Retourne une réponse avec le code de statut 201 (CREATED)
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // En cas d'erreur, logue l'erreur et retourne une réponse avec le code de statut 500 (INTERNAL_SERVER_ERROR)
            System.err.println("Erreur lors de l'envoi du message : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Supprimer un message par son ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        try {
            // Appelle le service pour supprimer un message
            messageService.supprimerMessagesUtilisateur(id);
            // Retourne une réponse avec le code de statut 204 (NO_CONTENT)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // En cas d'erreur, logue l'erreur et retourne une réponse avec le code de statut 500 (INTERNAL_SERVER_ERROR)
            System.err.println("Erreur lors de la suppression du message : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
