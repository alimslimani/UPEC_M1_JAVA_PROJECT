package AUTH;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by slima_000 on 12/11/2016.
 */

public class ConnexionPG {
    public static Connection ConnexionPG() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            Class.forName(prop.getProperty("driver"));
            Connection con = DriverManager.getConnection(prop.getProperty("ojdbc"), prop.getProperty("db_login"), prop.getProperty("db_pwd"));
            if (con != null) {
                //System.out.println("Authentification OK ");
                return con;
            } else {
                System.out.println("Acces denied!!!");
            }
        } catch (Exception e) {
            System.out.println("You don't have a driver to access to DataBASE");
            System.exit(0);
        }
        return ConnexionPG();
    }
}