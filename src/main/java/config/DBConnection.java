package config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class DBConnection {
    private String url= "jdbc:postgresql://localhost/your_database";

    private String username= "your_username";

    private String password= "your_password";
    @Bean
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            if (e.getMessage().contains("password authentication failed")) {
                System.out.println("Mot de passe incorrect !");
            } else if (e.getMessage().contains("database does not exist")) {
                System.out.println("Base de données introuvable !");
            } else {
                System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            }
        }
        return connection;
    }
}
