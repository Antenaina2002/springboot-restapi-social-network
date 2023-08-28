package model;

import lombok.*;
import java.sql.Timestamp;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Commentaire {
    private int id_commentaire;
    private String commentaire;
    private Timestamp commentaire_date;
    private int commentateurId;
    private int publicationId;
    public Commentaire(int id) {
        this.id_commentaire = id;
    }
    public Commentaire(int id, String commentaire, Timestamp date_commentaire, int commentateurId, int publicationId){
        this.id_commentaire = id;
        this.commentaire = commentaire;
        this.commentaire_date = date_commentaire;
        this.commentateurId = commentateurId;
        this.publicationId = publicationId;
    }
}