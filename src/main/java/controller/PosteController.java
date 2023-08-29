package controller;

import model.Poste;
import service.PosteService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@RestController
@RequestMapping(path = "/post")
public class PosteController {
    private PosteService postService;

    // Récupérer la liste de tous les posts
    @GetMapping("/all-posts")
    public List<Poste> listOfAllPosts() throws SQLException {
        return postService.getAllPostes();
    }

    // Récupérer un post par son ID
    @GetMapping("/{id}")
    public Optional<Poste> getPostById(@PathVariable int id) throws SQLException {
        return postService.getPosteById(id);
    }

    // Créer un nouveau post
    @PostMapping
    public void createNewPost(@RequestBody Poste poste) throws SQLException {
        postService.createPoste(poste);
    }

    // Mettre à jour un post par son ID
    @PutMapping("/{id}")
    public void updatePost(@PathVariable int id, @RequestBody Poste poste) throws SQLException {
        postService.updatePoste(id, poste);
    }

    // Supprimer un post par son ID
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable int id) throws SQLException {
        postService.deletePoste(id);
    }
}
