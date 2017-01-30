package ADMIN;

import AUTH.Auth_ADMIN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * Created by slima_000 on 12/11/2016.
 */

public class Log_ADMIN extends JFrame {
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField jTextField_LOGIN = new JTextField();
    JPasswordField jTextField_PASSWORD = new JPasswordField();
    JButton jButton_OK = new JButton();
    JButton jButton_CANCEL = new JButton();
    GridLayout gridLayout2 = new GridLayout(2, 1);
    GridLayout gridLayout3 = new GridLayout(2, 1);
    GridLayout gridLayout4 = new GridLayout();
    private String login;
    private String password;

    public Log_ADMIN() {
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
        jLabel1.setText("LOGIN");
        jLabel2.setText("PASSWORD");
        jTextField_LOGIN.setText("");
        jButton_OK.setText("ENTER");
        jButton_OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Enter_actionPerformed(e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jButton_CANCEL.setText("CANCEL");
        jButton_CANCEL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Annuler_actionPerformed(e);
            }
        });

        jPanel2.setLayout(gridLayout2);
        jPanel3.setLayout(gridLayout3);
        jPanel4.setLayout(gridLayout4);


        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, null);
        jPanel2.add(jLabel1, null);
        jPanel2.add(jLabel2, null);
        jPanel1.add(jPanel3, null);
        jPanel3.add(jTextField_LOGIN, null);
        jPanel3.add(jTextField_PASSWORD, null);
        this.getContentPane().add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(jButton_OK, null);
        jPanel4.add(jButton_CANCEL, null);
    }

    //fermer
    public void Annuler_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    //administration user
    public void Enter_actionPerformed(ActionEvent e) throws SQLException {
        String login, password;
        login = jTextField_LOGIN.getText();
        password = jTextField_PASSWORD.getText();
        if (isValidAdmin(login, password)) {
            Admin_space adminSpace = new Admin_space();
            adminSpace.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "LOGIN OR PASSWORD NOT VALIDE",
                    "MESSAGE ERROR",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //  identification de login'utilisateur
    public boolean isValidAdmin(String login, String password) throws SQLException {
        boolean valide = false;

        if (Auth_ADMIN.Auth(login, password).isValid(0)) {
            this.login = Auth_ADMIN.getLogin;
            this.password = Auth_ADMIN.getPassword;
        }
        if ((this.login.equals(login)) && (this.password.equals(password))) {
            valide = true;
            System.out.println("ACCES GRANTED ");
        } else if (!(this.login.equalsIgnoreCase(login)) || !(this.password.equalsIgnoreCase(password))) {
            valide = false;
            System.out.println("ACCES DENIED ");
        }
//        System.out.print(valide + "\n");
        return valide;
    }

}