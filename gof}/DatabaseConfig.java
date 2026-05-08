//Garantiza una única instancia. Uso de volatile y synchronized para Thread-Safety.

public class DatabaseConfig {
    private static volatile DatabaseConfig instance;
    private String url = "jdbc:mysql://localhost:3306/mydb";

    private DatabaseConfig() {} // Constructor privado preventivo

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) instance = new DatabaseConfig();
            }
        }
        return instance;
    }
}
