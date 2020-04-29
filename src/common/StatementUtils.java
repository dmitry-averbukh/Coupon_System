package common;

import java.sql.SQLException;
import java.sql.Statement;

public class StatementUtils {
    public static void close(Statement... statements) {
        for (Statement statement : statements) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
