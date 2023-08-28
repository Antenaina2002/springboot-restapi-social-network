package model;

import lombok.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Utilisateur {
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private char genre;
    private String email;
    private String telephone;
    private LocalDateTime date_inscription;
    public Utilisateur(int id) {
        this.id_utilisateur = id;
    }
    public Utilisateur(int id, String nom, String prenom, char genre, String email, String telephone, LocalDateTime date_inscription ){
        this.id_utilisateur = id;
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.email = email;
        this.telephone = telephone;
        this.date_inscription = date_inscription;
    }
}