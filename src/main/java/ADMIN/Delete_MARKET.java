package ADMIN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by alim on 16/11/16.
 */
public class Delete_MARKET extends JFrame {
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JComboBox jComboBox_Market = new JComboBox();
    JButton jButton_DelelteMarket = new JButton();
    JButton jButton_ShowMarket = new JButton();
    JButton jButton_Exit = new JButton();
    GridLayout gridLayout2 = new GridLayout(2, 1);
    GridLayout gridLayout3 = new GridLayout(2, 1);
    GridLayout gridLayout4 = new GridLayout();
    private Connection cnx;
    private Statement st;

    public Delete_MARKET() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(417, 100));
        jPanel1.setLayout(gridLayout1);
        jLabel1.setText("MARKET");
        jButton_DelelteMarket.setEnabled(false);
        jButton_ShowMarket.setText("SHOW jComboBox_Market");
        jButton_ShowMarket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_MARKET_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                jButton_DelelteMarket.setEnabled(true);
            }
        });

        jButton_DelelteMarket.setText("DELETE jComboBox_Market");
        jButton_DelelteMarket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DeleteMarket_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        jButton_Exit.setText("PREVIOUS");
        jButton_Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Close_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        jPanel2.setLayout(gridLayout2);
        jPanel3.setLayout(gridLayout3);
        jPanel4.setLayout(gridLayout4);
        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));
        jPanel4.setBackground(new java.awt.Color(0, 0, 255));

        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, null);
        jPanel2.add(jLabel1, null);
        jPanel2.add(jLabel2, null);
        jPanel1.add(jPanel3, null);
        jPanel3.add(jComboBox_Market, null);
        this.getContentPane().add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(jButton_Exit, null);
        jPanel4.add(jButton_ShowMarket, null);
        jPanel4.add(jButton_DelelteMarket, null);
    }

    @SuppressWarnings("unchecked")
    private void Close_actionPerformed(ActionEvent e) throws Exception {
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    private void DeleteMarket_actionPerformed(ActionEvent e) throws SQLException {
        //        PART OF DELETE jComboBox_Market IN DB
        String market;
        market = jComboBox_Market.getSelectedItem().toString();
        try {
            cnx = AUTH.ConnexionPG.ConnexionPG();
            st = cnx.createStatement();
            PreparedStatement statement = cnx.prepareStatement("SELECT login FROM market where login =?");
            statement.setString(1, market);
            if (statement.executeQuery().next()) {
                PreparedStatement sql = cnx.prepareStatement("DELETE FROM market WHERE login = ?");
                sql.setString(1, market);
                sql.executeUpdate();
                System.out.println("THE jComboBox_Market IS DELETED ");
            } else {
                System.out.println("THIS jComboBox_Market DOES NOT EXIST");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    private void Show_MARKET_actionPerformed(ActionEvent e) throws Exception {
        jComboBox_Market.removeAllItems();
        try {
            cnx = AUTH.ConnexionPG.ConnexionPG();
            st = cnx.createStatement();
            PreparedStatement statement = cnx.prepareStatement("SELECT login FROM market");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                jComboBox_Market.addItem(resultSet.getString("login"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


}
