package MARKET;

import AUTH.Auth_MARKET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by slima_000 on 12/11/2016.
 */

public class Log_MARKET extends JFrame {
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField login = new JTextField();
    JPasswordField jPasswordField = new JPasswordField();
    JButton jButton_OK = new JButton();
    JButton jButton_CANCEL = new JButton();
    GridLayout gridLayout2 = new GridLayout(2, 1);
    GridLayout gridLayout3 = new GridLayout(2, 1);
    GridLayout gridLayout4 = new GridLayout();
    private String log;
    private String pass;

    public Log_MARKET() {
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
        login.setText("");
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
        jPanel3.add(login, null);
        jPanel3.add(jPasswordField, null);
        this.getContentPane().add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(jButton_OK, null);
        jPanel4.add(jButton_CANCEL, null);
    }

    //jButton_CLOSE
    public void Annuler_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    //administration market
    public void Enter_actionPerformed(ActionEvent e) throws SQLException {
        String login, password;
        login = this.login.getText().toString();
        password = this.jPasswordField.getText().toString();
        if (isValidMarket(login, password)) {
            Market_space marketSpace = new Market_space();
            marketSpace.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Login OR Password NOT VALIDE",
                    "MESSAGE ERROR",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //  identification de login'utilisateur
    public boolean isValidMarket(String login, String password) throws SQLException {
        boolean valide = false;
        if (Auth_MARKET.Auth(login, password).isValid(0)) {
            log = Auth_MARKET.getLogin;
            pass = Auth_MARKET.getPassword;
        }
        if ((log.equals(login)) && (pass.equals(password))) {
            valide = true;
            System.out.println("ACCES GRANTED ");
        } else if (!(log.equalsIgnoreCase(login)) || !(pass.equalsIgnoreCase(password))) {
            valide = false;
            System.out.println("ACCES DENIED ");
        }
//        System.out.print(valide + "\n");
        return valide;
    }

}