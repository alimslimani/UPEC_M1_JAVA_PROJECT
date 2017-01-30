package AUTH;

import java.sql.Connection;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Disconnect {
    private static Connection connection;

    //method to disconnect client
    public static Connection Disconnect() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("You are disconnected".toUpperCase());
        }
        return connection;
    }
}
