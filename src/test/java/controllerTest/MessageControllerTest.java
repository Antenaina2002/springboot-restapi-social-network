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
        messageService = mock(MessageService.class);
        messageController = new MessageController(messageService);
    }

    @Test
    void testDisplayMessages() {
        int idSender = 1;
        int idReceiver = 2;
        List<Messages> messages = new ArrayList<>();
        messages.add(new Messages(/* initialize message 1 */));
        messages.add(new Messages(/* initialize message 2 */));
        when(messageService.recupererTousLesMessagesEntreUtilisateurs(idSender, idReceiver)).thenReturn(messages);

        ResponseEntity<List<Messages>> responseEntity = messageController.displayMessages(idSender, idReceiver);
        List<Messages> response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(messages, response);
        System.out.println("testDisplayMessages réussi");
    }

    @Test
    void testSendNewMessage() {
        Messages newMessage = new Messages(/* initialize new message */);

        ResponseEntity<Void> responseEntity = messageController.sendNewMessage(newMessage);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        System.out.println("testSendNewMessage réussi");
    }

    @Test
    void testDeleteMessage() {
        int messageId = 1;

        ResponseEntity<Void> responseEntity = messageController.deleteMessage(messageId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        System.out.println("testDeleteMessage réussi");
    }

}
