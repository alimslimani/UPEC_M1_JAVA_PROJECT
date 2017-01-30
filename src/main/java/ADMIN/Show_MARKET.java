package ADMIN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by alim on 16/11/16.
 */
public class Show_MARKET extends JFrame {
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JComboBox jComboBox_Market = new JComboBox();
    JButton jButton_SHOW = new JButton();
    JButton jButton_EXIT = new JButton();
    GridLayout gridLayout2 = new GridLayout(2, 1);
    GridLayout gridLayout3 = new GridLayout(2, 1);
    GridLayout gridLayout4 = new GridLayout();
    private Connection connection;
    private Statement statement;

    public Show_MARKET() {
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

        jButton_SHOW.setText("SHOW_MARKET");
        jButton_SHOW.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        jButton_EXIT.setText("PREVIOUS");
        jButton_EXIT.addActionListener(new ActionListener() {
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
        jPanel4.add(jButton_SHOW, null);
        jPanel4.add(jButton_EXIT, null);
    }

    private void Close_actionPerformed(ActionEvent e) throws Exception {
        this.dispose();
    }

    private void Show_actionPerformed(ActionEvent e) throws SQLException {
        //        PART OF SHOW_MARKET jComboBox_Market IN DB
        jComboBox_Market.removeAllItems();
        try {
            connection = AUTH.ConnexionPG.ConnexionPG();
            statement = connection.createStatement();
            PreparedStatement statement = connection.prepareStatement("SELECT login FROM market");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                jComboBox_Market.addItem(resultSet.getString("login"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
