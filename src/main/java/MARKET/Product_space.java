package MARKET;

import AUTH.Auth_MARKET;
import Model.Method;
import Model.Product;
import Model.SuperMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Product_space extends JFrame implements Method {
    private static List<Product> productList = new ArrayList<>();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JButton jButton_AddProduct = new JButton();
    JButton jButton_Stock = new JButton();
    JButton close = new JButton();
    GridLayout gridLayout2 = new GridLayout();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);
    private BufferedReader reader;
    private String line;
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private int quantity;
    private float price;
    private String type_product;
    private String type_displays;

    public Product_space() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(300, 300));
        this.setTitle("ENTER PRODUCT ");

        jButton_AddProduct.setText("ADD PRODUCT");
        jButton_AddProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addProduct(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            private void addProduct(ActionEvent e) throws IOException {
                AddProduct addProduct = new AddProduct();
                addProduct.setVisible(true);
            }
        });


        jButton_Stock.setText("SHOW STOCKS");
        jButton_Stock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    readDataFromFileSrc();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                if (!productList.isEmpty()) {
                    System.out.println("This is a list of products :".toUpperCase());
                    productList.forEach(x -> System.out.println("Libele : " + x.getName_product()
                            + "- Label : " + x.getLabel()
                            + "- Type produit : " + x.getType_product()
                            + "- Type etalage : " + x.getType_displays()
                            + "- DDF : " + format.format(x.getDDF())
                            + " - DLC : " + format.format(x.getDLC())
                            + " - Quantity : " + x.getQuantity()
                            + " - Price : " + x.getPrice()));
                } else {
                    System.out.println("No product in this market".toUpperCase());
                }
            }
        });

        close.setText("PREVIOUS");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close_ActionPerformed(e);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jPanel1.setLayout(gridLayout2);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(close, null);
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jButton_AddProduct, null);
        jPanel2.add(jButton_Stock, null);
        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
    }

    //    jButton_CLOSE
    public void Close_ActionPerformed(ActionEvent e) {
        this.dispose();
    }

    //     Read text file with data from Market and add it in List
    @Override
    public void readDataFromFileSrc() throws ParseException {
        productList.removeAll(productList);
        try {
            File file = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                reader = new BufferedReader(new FileReader("List Product of Market " + superMarket.getName().toString() + ".txt"));
                if (!reader.ready()) {
                    throw new IOException();
                }
                if (productList.isEmpty()) {
                    while ((line = reader.readLine()) != null) {

                        //      index's of informations to get product in file and add it in List<Product>
                        int index_Libele1 = line.indexOf("Libele : ");
                        int index_Libele2 = line.indexOf("- Label");

                        int index_Label1 = line.indexOf("Label : ");
                        int index_Label2 = line.indexOf("- Type produit");

                        int index_TypeProduit1 = line.indexOf("Type produit : ");
                        int index_TypeProduit2 = line.indexOf("- Type etalage");

                        int index_TypeEtalage1 = line.indexOf("Type etalage : ");
                        int index_TypeEtalage2 = line.indexOf("- DDF");

                        int index_DDF1 = line.indexOf("DDF : ");
                        int index_DDF2 = line.indexOf("- DLC");

                        int index_DLC1 = line.indexOf("DLC : ");
                        int index_DLC2 = line.indexOf("- Quantity");

                        int index_Quantity1 = line.indexOf("Quantity : ");
                        int index_Quantity2 = line.indexOf(" - Price");

                        int index_Prix1 = line.indexOf("Price : ");
                        int length = line.length();

                        name_product = line.substring(index_Libele1 + 9, index_Libele2);
                        nameOfLabel = line.substring(index_Label1 + 8, index_Label2);
                        type_product = line.substring(index_TypeProduit1 + 15, index_TypeProduit2);
                        type_displays = line.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
                        DDF = format.parse(line.substring(index_DDF1 + 6, index_DDF2));
                        DLC = format.parse(line.substring(index_DLC1 + 6, index_DLC2));
                        quantity = Integer.parseInt(line.substring(index_Quantity1 + 11, index_Quantity2));
                        String c = String.valueOf(line.substring(line.indexOf("Price : "), length));
                        price = Float.valueOf(c.substring(8));
                        productList.add(new Product(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price));
//                    }
                    }
                }
                reader.close();
            } else if (file.exists() && file.length() == 0) {
                System.out.println("No Products in this Market");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void readDataFromFileDst() {

    }

    @Override
    public void saveDataInFile() {

    }
}

