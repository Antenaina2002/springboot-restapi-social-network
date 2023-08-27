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
    public Connection getConnection() throws SQLException{
        // On utilise DriverManager pour établir une connexion à la base de données.
        // Les paramètres de connexion sont récupérés à partir des champs précédemment définis.
        return DriverManager.getConnection(url,username,password);
    }
}
