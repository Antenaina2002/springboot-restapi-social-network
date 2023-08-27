package model;

import lombok.*;
import java.sql.Timestamp;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Poste {
    private int id_poste;
    private Timestamp date_poste;
    private int auteurId;
    private int publicationId;
    public Poste(int id) {
        this.id_poste = id;
    }
    public Poste(int id, Timestamp date_poste, int auteurIdPost, int publicationId ){
        this.id_poste = id;
        this.date_poste = date_poste;
        this.auteurId = auteurIdPost;
        this.publicationId = publicationId;
    }
}