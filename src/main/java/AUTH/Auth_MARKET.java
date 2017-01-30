package AUTH;

import Model.Type_Market;

import java.sql.*;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Auth_MARKET {

    public static String getLogin;
    public static String getPassword;
    public static String getTypeMarket;
    public static Type_Market typeMarket;
    static Connection connection;
    static Statement statement;
    static ResultSet query_Market;
    static boolean connected = false;

    public static Connection Auth(String login, String mdp) throws SQLException {
        try {
            //Appel la methode ConnectBase de ConnectDB pour faire la requete si le login et le MDP exite
            connection = ConnexionPG.ConnexionPG();
            statement = connection.createStatement();
            //utilisation de PreparedStatement afin d'eviter les injection SQL
            //pour securiser ll'acces aux donnees
            PreparedStatement select_market = connection.prepareStatement("SELECT login,mdp,typemarket\n" +
                    "FROM market");
            //Les champs de la methode qui se remplissent lors de la saisie
            //Execution de la requete
            query_Market = select_market.executeQuery();
            while (query_Market.next()) {
                if (query_Market.getString(1).equals(login) && query_Market.getString(2).equals(mdp)) {
                    connected = true;
                    getLogin = query_Market.getString(1);
                    getPassword = query_Market.getString(2);
                    getTypeMarket = query_Market.getString(3);
                    typeMarket = Type_Market.valueOf(getTypeMarket);
                    System.out.print("LOGGED AS :");
                    System.out.println(query_Market.getString("login") + " ");
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
