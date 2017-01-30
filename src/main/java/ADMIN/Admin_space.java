package ADMIN;

import AUTH.Disconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Admin_space extends JFrame {

    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JButton addMarket = new JButton();
    JButton jButton_DeleteMarket = new JButton();
    JButton jButton_ShowMarket = new JButton();
    JButton jButton_Quit = new JButton();
    JButton jButton_Close = new JButton();
    GridLayout gridLayout = new GridLayout();

    public Admin_space() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(600, 373));
        this.setTitle("ADMINISTRATION");

        addMarket.setText("ADD MARKET");
        addMarket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddMarket_actionPerformed(e);
            }
        });

        jButton_DeleteMarket.setText("DELETE MARKET");
        jButton_DeleteMarket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Delete_Market_actionPerformed(e);
            }
        });

        jButton_ShowMarket.setText("SHOW_MARKET");
        jButton_ShowMarket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Show_Market_actionPerformed(e);
            }
        });


        jButton_Quit.setText("QUIT");
        jButton_Quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jButton_Close.setText("CLOSE");
        jButton_Close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CLOSE_actionPerformed(e);
            }
        });

        jPanel1.setBackground(new java.awt.Color(20, 50, 200));

        jPanel2.setBackground(new java.awt.Color(20, 50, 200));

        jPanel1.setLayout(gridLayout);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton_Close, null);
        jPanel1.add(jButton_Quit, null);
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(addMarket, null);
        jPanel2.add(jButton_DeleteMarket, null);
        jPanel2.add(jButton_ShowMarket, null);
    }

    private void Show_Market_actionPerformed(ActionEvent e) {
        Show_MARKET show_market = new Show_MARKET();
        show_market.setVisible(true);
    }

    private void Delete_Market_actionPerformed(ActionEvent e) {
        Delete_MARKET delete_market = new Delete_MARKET();
        delete_market.setVisible(true);
    }

    private void AddMarket_actionPerformed(ActionEvent e) {
        Add_MARKET add_market = new Add_MARKET();
        add_market.setVisible(true);
    }

    public void CLOSE_actionPerformed(ActionEvent e) {
        Disconnect.Disconnect();
        this.dispose();
    }
}
