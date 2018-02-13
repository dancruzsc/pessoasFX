package pessoas;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe Factory responsável pela criação de conexões com o banco MySQL.
 * @author Danilo Cruz
 */

public class ConnectionFactory {
    
    /**
     * Método responsável pela criação de novas conexões com o banco MySQL.
     * @return {@link Connection} responsável por consultas ao banco.
     */
    public Connection getConnection() {
        try {
            // URI responsável pela conexão com o schema correto
            String uri = "jdbc:mysql://localhost:3306/pessoas_app";
            return DriverManager.getConnection(uri, "sattra", "sattra");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
