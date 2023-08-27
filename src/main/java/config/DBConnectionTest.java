package config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionTest {

    public static void main(String[] args) {
        // Create an instance of the DBConnection class
        DBConnection dbConnection = new DBConnection();

        // Get the database connection
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try {
                // Create a statement object to execute SQL queries
                Statement statement = connection.createStatement();

                // Execute a simple query to retrieve data from a table
                ResultSet resultSet = statement.executeQuery("SELECT * FROM utilisateur");

                // Process the result set
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    int id_utilisateur = resultSet.getInt("id_utilisateur");
                    String nom = resultSet.getString("nom");

                    // Print the retrieved data
                    System.out.println("ID: " + id_utilisateur + ", Name: " + nom);
                }

                // Close the result set, statement, and connection
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                // Handle any SQL exceptions
                System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            }
        } else {
            System.out.println("La connexion à la base de données a échoué !");
        }
    }
}
