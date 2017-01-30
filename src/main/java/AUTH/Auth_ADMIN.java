package AUTH;

import java.sql.*;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Auth_ADMIN {

    public static String getLogin;
    public static String getPassword;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet get_Auth;
    private static boolean connected = false;

    public static Connection Auth(String login, String mdp) throws SQLException {
        try {
            //Appel la methode ConnectBase de ConnectDB pour faire la requete si le login et le MDP exite
            connection = ConnexionPG.ConnexionPG();
            statement = connection.createStatement();
            //utilisation de PreparedStatement afin d'eviter les injection SQL
            //pour securiser ll'acces aux donnees
            PreparedStatement select_admin = connection.prepareStatement("select login,mdp from admin");
            //Les champs de la methode qui se remplissent lors de la saisie


            //Execution de la requete
            get_Auth = select_admin.executeQuery();
            while (get_Auth.next()) {
                System.out.println(get_Auth.getString(1)+get_Auth.getString(2));
                if (get_Auth.getString(1).equals(login) && get_Auth.getString(2).equals(mdp)) {
                    connected = true;
                    getLogin = get_Auth.getString(1);
                    getPassword = get_Auth.getString(2);
                    System.out.print("LOGGED AS :");
                    System.out.println(get_Auth.getString("login") + " ");
                    break;
                } else {
                    getLogin = " ";
                    getPassword = " ";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
