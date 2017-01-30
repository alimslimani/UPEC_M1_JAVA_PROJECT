package ADMIN;

import Model.Type_Market;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alim on 16/11/16.
 */
public class Add_MARKET extends JFrame {
    private static List<String> stringList = new ArrayList<>();

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JTextField jTextField_LOGIN = new JTextField();
    JTextField jTextField_CITY = new JTextField();
    JPasswordField jPasswordField_PASSWORD = new JPasswordField();
    JButton jButton_OK = new JButton();
    JButton jButton_CANCEL = new JButton();
    JButton jButton_CLOSE = new JButton();
    GridLayout gridLayout1 = new GridLayout(1, 2);
    GridLayout gridLayout2 = new GridLayout(10, 1);
    GridLayout gridLayout3 = new GridLayout(10, 1);
    GridLayout gridLayout4 = new GridLayout();
    JPanel LesLables = new JPanel();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel LesTextFields = new JPanel();
    JComboBox type_market = new JComboBox(Type_Market.values());
    private Connection connection;
    private Statement statement;
    private ResultSet query_Market;

    public Add_MARKET() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Deprecated()
    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(400, 350));
        this.setTitle("       ADD NEW jComboBox_Market       ");
        jPanel1.setLayout(gridLayout1);

        jLabel1.setText("MARKET");
        jLabel2.setText("PASSWORD");
        jLabel3.setText("CITY");
        jLabel4.setText("TYPE jComboBox_Market");

        jTextField_LOGIN.setText("");
        jPasswordField_PASSWORD.setText("");
        jTextField_CITY.setText("");

        LesLables.setLayout(gridLayout2);

        LesTextFields.setLayout(gridLayout3);

        jButton_CANCEL.setText("CANCEL");
        jButton_CANCEL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Cancel_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        jButton_OK.setText("ADD");
        jButton_OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Ok_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        jButton_CLOSE.setText("PREVIOUS");
        jButton_CLOSE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Close_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        jPanel2.setLayout(gridLayout4);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButton_OK, null);
        jPanel2.add(jButton_CANCEL, null);
        jPanel2.add(jButton_CLOSE, null);

        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jPanel1.add(LesLables, null);
        LesLables.add(jLabel1, null);
        LesLables.add(jLabel2, null);
        LesLables.add(jLabel3, null);
        LesLables.add(jLabel4, null);

        jPanel1.add(LesTextFields, null);
        LesTextFields.add(jTextField_LOGIN, null);
        LesTextFields.add(jPasswordField_PASSWORD, null);
        LesTextFields.add(jTextField_CITY, null);
        LesTextFields.add(type_market, null);

    }

    private void Close_actionPerformed(ActionEvent e) throws Exception {
        this.dispose();
    }

    private void Ok_actionPerformed(ActionEvent e) throws SQLException {
        //        PART OF ADD jComboBox_Market IN DB
        if (jTextField_LOGIN.getText().equals("") || jPasswordField_PASSWORD.getText().equals("") || jTextField_CITY.getText().equals("")) {
            System.out.println("CHECK IF LOGIN OR PASSWORD NOT NULL");
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs concernant ce magasin SVP !".toUpperCase(),
                    "MESSAGE ERROR",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            String market, mdp, city;
            market = jTextField_LOGIN.getText();
            mdp = jPasswordField_PASSWORD.getText();
            city = jTextField_CITY.getText();
            try {
                connection = AUTH.ConnexionPG.ConnexionPG();
                statement = connection.createStatement();
                PreparedStatement select_market = connection.prepareStatement("SELECT login,city\n" +
                        "FROM market");
                //Les champs de la methode qui se remplissent lors de la saisie
                //Execution de la requete
                query_Market = select_market.executeQuery();
                stringList.removeAll(stringList);
                while (query_Market.next()) {
                    String login = query_Market.getString(1);
                    String city1 = query_Market.getString(2);
                    stringList.add(login);
                    stringList.add(city1);
                }
//                System.out.println(stringList);
                if (stringList.contains(market) && stringList.contains(city)) {
                    System.out.println("THIS jComboBox_Market EXIST");
                    JOptionPane.showMessageDialog(null, "THIS jComboBox_Market EXIST !".toUpperCase(),
                            "MESSAGE ERROR",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    PreparedStatement sql = connection.prepareStatement("INSERT INTO market(login,mdp,typemarket,city) VALUES (?,?,?,?)");
                    sql.setString(1, market);
                    sql.setString(2, mdp);
                    sql.setString(3, type_market.getSelectedItem().toString());
                    sql.setString(4, city);
                    sql.executeUpdate();
                    System.out.println("THE jComboBox_Market IS ADDED ");
                    jTextField_LOGIN.setText("");
                    jPasswordField_PASSWORD.setText("");
                    jTextField_CITY.setText("");

                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void Cancel_actionPerformed(ActionEvent e) throws Exception {
        jTextField_LOGIN.setText("");
        jPasswordField_PASSWORD.setText("");
    }
}
