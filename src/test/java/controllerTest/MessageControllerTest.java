package controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import controller.MessageController;
import model.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.MessageService;

class MessageControllerTest {

    private MessageService messageService;
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        // Création du mock pour le service
        messageService = mock(MessageService.class);
        messageController = new MessageController(messageService);
    }

    @Test
    void testDisplayMessages() {
        // Arrange : Préparation des données de test
        int idSender = 1;
        int idReceiver = 2;
        List<Messages> messages = new ArrayList<>();
        messages.add(new Messages(/* initialize message 1 */));
        messages.add(new Messages(/* initialize message 2 */));
        // Configuration du comportement du mock
        when(messageService.recupererTousLesMessagesEntreUtilisateurs(idSender, idReceiver)).thenReturn(messages);

        // Act : Exécution de la méthode à tester
        ResponseEntity<List<Messages>> responseEntity = messageController.displayMessages(idSender, idReceiver);
        List<Messages> response = responseEntity.getBody();

        // Assert : Vérification des résultats
        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(messages, response);
        System.out.println("testDisplayMessages réussi");
    }

    @Test
    void testSendNewMessage() {
        // Arrange : Préparation des données de test
        Messages newMessage = new Messages(/* initialize new message */);

        // Act : Exécution de la méthode à tester
        ResponseEntity<Void> responseEntity = messageController.sendNewMessage(newMessage);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        System.out.println("testSendNewMessage réussi");
    }

    @Test
    void testDeleteMessage() {
        // Arrange : Préparation des données de test
        int messageId = 1;

        // Act : Exécution de la méthode à tester
        ResponseEntity<Void> responseEntity = messageController.deleteMessage(messageId);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        System.out.println("testDeleteMessage réussi");
    }

}
