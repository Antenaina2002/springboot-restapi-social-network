package model;

import lombok.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Messages {
    private int id_message;
    private int envoyeur_id;
    private int receveur_id;
    private String message_text;
    private LocalDateTime message_date;
    public Messages(int id) {
        this.id_message = id;
    }
    public Messages(int id, int id_envoyeur, int id_receveur, String contenu_message, LocalDateTime date_message){
        this.id_message = id;
        this.envoyeur_id = id_envoyeur;
        this.receveur_id = id_receveur;
        this.message_text = contenu_message;
        this.message_date = date_message;
    }
}