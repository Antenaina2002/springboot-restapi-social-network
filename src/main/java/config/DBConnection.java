package config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.sql.*;

    // On utilise l'annotation @Component pour indiquer à Spring que cette classe est un bean
    // et qu'elle doit être prise en compte lors de la création du contexte de l'application.
@Component
public class DBConnection {
    // On utilise l'annotation @Value pour injecter la valeur d'une propriété de configuration
    // dans les champs correspondants. Les valeurs sont définies dans le fichier application.properties de Spring.
    private String url= "jdbc:postgresql://localhost/your_database";

    private String username= "your_username";

    private String password= "your_password";

    // On utilise l'annotation @Bean pour indiquer à Spring que cette méthode doit être utilisée pour créer un bean.
    // Le bean est ensuite géré par le conteneur Spring et peut être injecté dans d'autres parties de l'application.
    @Bean
    public Connection getConnection() {
        Connection connection = null;
        try {
            // On utilise DriverManager pour établir une connexion à la base de données.
            // Les paramètres de connexion sont récupérés à partir des champs précédemment définis.
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // Si une exception SQLException est levée, on vérifie le message d'erreur pour déterminer la cause de l'erreur.
            if (e.getMessage().contains("password authentication failed")) {
                // Si le message d'erreur contient "password authentication failed", on affiche "Mot de passe incorrect !".
                System.out.println("Mot de passe incorrect !");
            } else if (e.getMessage().contains("database does not exist")) {
                // Si le message d'erreur contient "database does not exist", on affiche "Base de données introuvable !".
                System.out.println("Base de données introuvable !");
            } else {
                // Sinon, on affiche le message d'erreur générique.
                System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            }
        }
        return connection;
    }
}
