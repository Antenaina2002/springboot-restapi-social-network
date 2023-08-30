package model;

import lombok.*;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Publication {
    private int id_publication;
        private String contenu;
    public Publication(int id) {
            this.id_publication = id;
    }
    public Publication(int id, String content){
        this.id_publication = id;
        this.contenu = content;
    }
}