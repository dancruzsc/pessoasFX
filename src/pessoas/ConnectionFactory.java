package pessoas;

import java.sql.Connection;
import java.sql.DriverManager;

    // Classe responsável pela criação de conexões.

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/pessoas_app?useSSL=false"
                    , "sattra", "sattra");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
