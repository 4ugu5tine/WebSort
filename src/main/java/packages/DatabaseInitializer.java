package packages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {


        private static final String URL = "jdbc:postgresql://localhost:5432/books";
        private static final String USER = "postgres";
        private static final String PASSWORD = "work";

        public static void main(String[] args) {
            String createTablesSQL = """
                  CREATE TABLE IF NOT EXISTS books (
                  bookId SERIAL PRIMARY KEY,
                  title VARCHAR(255) NOT NULL,
                  author VARCHAR(255) NOT NULL,
                  copies INTEGER NOT NULL
              );
        """;

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTablesSQL);
                System.out.println("Tables created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


}

