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
    @Value("${PG_URL}")
    private String url;

    @Value("${PG_USERNAME}")
    private String username;

    @Value("${PG_PASSWORD}")
    private String password;

    // On utilise l'annotation @Bean pour indiquer à Spring que cette méthode doit être utilisée pour créer un bean.
    // Le bean est ensuite géré par le conteneur Spring et peut être injecté dans d'autres parties de l'application.
    @Bean
    public Connection getConnection() throws SQLException{
        // On utilise DriverManager pour établir une connexion à la base de données.
        // Les paramètres de connexion sont récupérés à partir des champs précédemment définis.
        return DriverManager.getConnection(url,username,password);
    }
}
