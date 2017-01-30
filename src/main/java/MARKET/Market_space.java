package MARKET;

import AUTH.Auth_MARKET;
import Model.SuperMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Market_space extends JFrame {
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JButton jButton_AddProduct = new JButton();
    JButton jButton_AddPromotion = new JButton();
    JButton jButton_TransfertProduct = new JButton();
    JButton jButton_ShowQuantitySoldByProduct = new JButton();
    JButton jButton_Stock = new JButton();
    JButton jButton_Displays = new JButton();
    JButton jButton_SALE = new JButton();
    JButton jButton_QUIT = new JButton();
    GridLayout gridLayout = new GridLayout();
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);

    public Market_space() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(true);
        this.setSize(new Dimension(600, 425));
        this.setTitle("ADMINISTRATION OF " + superMarket.getType_market().toString() + " : " + superMarket.getName());

        jButton_AddProduct.setText("PRODUCT");
        jButton_AddProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addProduct(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            private void addProduct(ActionEvent e) throws IOException {
                Product_space productSpace = new Product_space();
                productSpace.setVisible(true);
            }
        });

        jButton_AddPromotion.setText("PROMOTION");
        jButton_AddPromotion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Promotion_space promotion_space = new Promotion_space();
                promotion_space.setVisible(true);
            }
        });

        jButton_TransfertProduct.setText("TRANSFER OF PRODUCT TO ANOTHER MARKET");
        jButton_TransfertProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TrasnferProduct_ActionPerformed(e);
//                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

            private void TrasnferProduct_ActionPerformed(ActionEvent e) {
                Transfert_PRODUCT transfert_product = new Transfert_PRODUCT();
                transfert_product.setVisible(true);
            }
        });

        jButton_Stock.setText("SHOW STOCKS");
        jButton_Stock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Show_Stock show_stock = new Show_Stock();
                show_stock.setVisible(true);
            }
        });

        jButton_Displays.setText("SHOW BY DISPLAYS");
        jButton_Displays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addProduct(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            private void addProduct(ActionEvent e) throws IOException {
                Show_StockByDisplays show_stockByDisplays = new Show_StockByDisplays();
                show_stockByDisplays.setVisible(true);
            }
        });

        jButton_SALE.setText("SALE PRODUCT");
        jButton_SALE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Sale_Product(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            private void Sale_Product(ActionEvent e) throws IOException {
                SALE_PRODUCT sale_product = new SALE_PRODUCT();
                sale_product.setVisible(true);

            }
        });

        jButton_ShowQuantitySoldByProduct.setText("SHOW LIST BY PRODUCT SALE");
        jButton_ShowQuantitySoldByProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                TrasnferProduct_ActionPerformed(e);
//                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ShowQuantitySoldByProduct showQuantitySoldByProduct = new ShowQuantitySoldByProduct();
                showQuantitySoldByProduct.setVisible(true);
            }

            private void TrasnferProduct_ActionPerformed(ActionEvent e) {
                Transfert_PRODUCT transfert_product = new Transfert_PRODUCT();
                transfert_product.setVisible(true);
            }
        });

        jButton_QUIT.setText("QUIT");
        jButton_QUIT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));
        jPanel1.setLayout(gridLayout);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton_QUIT, null);
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jButton_AddProduct, null);
        jPanel2.add(jButton_Displays, null);
        jPanel2.add(jButton_Stock, null);
        jPanel2.add(jButton_AddPromotion, null);
        jPanel2.add(jButton_TransfertProduct, null);
        jPanel2.add(jButton_ShowQuantitySoldByProduct, null);
        jPanel2.add(jButton_SALE, null);
        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
    }

    //    jButton_CLOSE
    public void CLOSE_actionPerformed(ActionEvent e) {
        AUTH.Disconnect.Disconnect();
        this.dispose();
    }
}

