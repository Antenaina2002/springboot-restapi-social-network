package repository;

import lombok.Getter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class repository<T> {
    private Connection connection; // La connexion à la base de données

    // Constructeur pour initialiser la connexion
    public repository(Connection connection) {
        this.connection = connection;
    }

    // Méthode abstraite pour insérer un objet dans la base de données
    public abstract void insert(T toInsert) throws SQLException;

    // Méthode abstraite pour récupérer tous les objets de la base de données
    public abstract List<T> findAll() throws SQLException;

    // Méthode abstraite pour mettre à jour un objet par son ID dans la base de données
    public abstract void updateById(int id, T object) throws SQLException;

    // Méthode abstraite pour récupérer un objet par son ID depuis la base de données
    public abstract Optional<T> findById(int id) throws SQLException;

    // Méthode abstraite pour supprimer un objet par son ID de la base de données
    public abstract void deleteById(int id) throws SQLException;
}
