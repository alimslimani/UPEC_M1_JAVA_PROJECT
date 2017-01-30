package MARKET;

import AUTH.Auth_MARKET;
import AUTH.ConnexionPG;
import Model.Inter;
import Model.Method;
import Model.Product;
import Model.SuperMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class Transfert_PRODUCT extends JFrame implements Method {
    private static List arrayListSrc = new ArrayList<>();
    private static List arrayListDest = new ArrayList<>();
    private static Connection connection;
    private static List<Product> productsDst = new ArrayList<>();
    private static List<Product> productsSrc = new ArrayList<>();
    JButton close = new JButton();
    JButton show_Displays = new JButton();
    JButton show_Market = new JButton();
    JButton show_ProductOfMarket = new JButton();
    JButton show_QuantityOfProduct = new JButton();
    JButton jButton_SendProduct = new JButton();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel LesTextFields = new JPanel();
    GridLayout gridLayout1 = new GridLayout(1, 2);
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    GridLayout gridLayout2 = new GridLayout(10, 1);
    GridLayout gridLayout3 = new GridLayout(10, 1);
    JTextField JTextField_Quantity = new JTextField();
    JTextField JTextField_ChoseQuantity = new JTextField();
    JComboBox jComboBox_ListOfMarketSrc = new JComboBox();
    JComboBox jComboBox_ListOfMarketDst = new JComboBox();
    JComboBox jComboBox_ListOfDisplays = new JComboBox();
    JComboBox jComboBox_ListProductOfMarket = new JComboBox();
    GridLayout gridLayout4 = new GridLayout();
    JPanel LesLables = new JPanel();
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);
    private String getLogin;
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private int quantity;
    private float price;
    private String type_product;
    private String type_displays;
    private BufferedReader br;
    private String lineSrc;
    private String lineDst;
    private Statement statement;
    private ResultSet get_Market;
    private boolean connected = false;

    public Transfert_PRODUCT() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> Set<T> mapProduct(List<Product> ls, Inter<T> func) {
        Set<T> result = new TreeSet<>();
        for (Product a : ls)
            result.add(func.f(a));
        return result;
    }

    public static Set<Integer> getQuantity(List<Product> productList) {
        Inter<Integer> i = a -> a.getQuantity();
        return mapProduct(productList, i);
    }

    public static Set<String> getName(List<Product> productList) {
        Inter<String> i = a -> a.getName_product();
        return mapProduct(productList, i);
    }

    private void jbInit() throws Exception {
        this.setResizable(true);
        this.setSize(new Dimension(950, 350));
        this.setTitle("       TRANSFERT PRODUCT        ");

        show_ProductOfMarket.setEnabled(false);
        show_Displays.setEnabled(false);
        show_QuantityOfProduct.setEnabled(false);

        JTextField_Quantity.setEditable(false);
        JTextField_ChoseQuantity.setEditable(true);
        jButton_SendProduct.setEnabled(false);
        jPanel1.setLayout(gridLayout1);
        jLabel1.setText("     MARKET DISTINATION      ");
        jLabel2.setText("     DISPLAYS      ");
        jLabel3.setText("     SELECT PRODUCT     ");
        jLabel4.setText("     QUANTITY     ");
        jLabel5.setText("     CHOSE QUANTITY TO SEND      ");
        LesLables.setLayout(gridLayout2);
        LesTextFields.setLayout(gridLayout3);

        show_Market.setText("SHOW MARKET");
        show_Market.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowMarket_ActionPerformed(e);
            }

            private void ShowMarket_ActionPerformed(ActionEvent e) {
                JTextField_Quantity.removeAll();
                JTextField_ChoseQuantity.removeAll();
                jComboBox_ListOfMarketSrc.removeAllItems();
//                jComboBox_ListOfMarketDst.removeAllItems();
                try {
                    //Appel la methode ConnectBase de ConnectDB pour faire la requete si le login et le MDP exite
                    connection = ConnexionPG.ConnexionPG();
                    statement = connection.createStatement();
                    //utilisation de PreparedStatement afin d'eviter les injection SQL
                    //pour securiser ll'acces aux donnees
                    PreparedStatement select_market = connection.prepareStatement("select login from market");
                    //Les champs de la methode qui se remplissent lors de la saisie
                    //Execution de la requete
                    get_Market = select_market.executeQuery();
                    while (get_Market.next()) {
                        connected = true;
                        getLogin = get_Market.getString(1);
//                        jComboBox_ListOfMarketSrc.addItem(getLogin);
                        jComboBox_ListOfMarketDst.addItem(getLogin);
                        jComboBox_ListOfMarketDst.removeItem(superMarket.getName().toString());
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                show_Displays.setEnabled(true);
            }
        });

        show_Displays.setText("SHOW DISPLAYS");
        show_Displays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ShowDisplays_ActionPerformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void ShowDisplays_ActionPerformed(ActionEvent e) throws ParseException {
                readDataFromFileSrc();
                jComboBox_ListOfDisplays.removeAllItems();
                JTextField_Quantity.removeAll();
                JTextField_ChoseQuantity.removeAll();
                if (productsSrc.isEmpty()) {
                    System.out.println("NO PRODUCTS");
                } else if (!productsSrc.isEmpty()) {
//                    System.out.println(productsSrc.size());
                    if (productsSrc.size() == 1) {
                        String c = productsSrc.get(productsSrc.size()).getType_displays();
                        jComboBox_ListOfDisplays.addItem(c);
                    } else if (productsSrc.size() == 2) {
                        String c = productsSrc.get(0).getType_displays();
                        String c1 = productsSrc.get(1).getType_displays();
                        if (c.equals(c1)) {
                            jComboBox_ListOfDisplays.addItem(c);
                        } else {
                            jComboBox_ListOfDisplays.addItem(c);
                            jComboBox_ListOfDisplays.addItem(c1);
                        }
                    } else if (productsSrc.size() > 2) {
                        for (int i = 1; i < productsSrc.size(); i++) {
                            String c = productsSrc.get(i - 1).getType_displays();
                            String c1 = productsSrc.get(i).getType_displays();
                            if (!c.equals(c1)) {
                                jComboBox_ListOfDisplays.addItem(c1);
                            }
                        }
                    }
                }
                show_ProductOfMarket.setEnabled(true);
            }
        });

        show_ProductOfMarket.setText("SHOW PRODUCT");
        show_ProductOfMarket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ShowProduct_ActionPerformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void ShowProduct_ActionPerformed(ActionEvent e) throws ParseException {
                productsSrc.removeAll(productsSrc);
                jComboBox_ListProductOfMarket.removeAllItems();
                JTextField_Quantity.removeAll();
                JTextField_ChoseQuantity.removeAll();
                readDataFromFileSrc();
                for (int i = 0; i < productsSrc.size(); i++) {
                    if (jComboBox_ListOfDisplays.getSelectedItem().equals(productsSrc.get(i).getType_displays())) {
                        jComboBox_ListProductOfMarket.addItem(String.valueOf(productsSrc.get(i).getName_product()));
                    }
                }
                show_QuantityOfProduct.setEnabled(true);
//              Affichage de la liste des produits par PRIX saisie dans l'interface
                System.out.println("PRODUCT LIST OF " + superMarket.getName().toString() + getName(productsSrc));
            }
        });

        show_QuantityOfProduct.setText("SHOW QUANTITY");
        show_QuantityOfProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowQuantity_ActionPerformed(e);
            }

            private void ShowQuantity_ActionPerformed(ActionEvent e) {
                jButton_SendProduct.setEnabled(true);
                for (int i = 0; i < productsSrc.size(); i++) {
                    if (jComboBox_ListProductOfMarket.getSelectedItem().equals(productsSrc.get(i).getName_product())) {
                        JTextField_Quantity.setText(String.valueOf(productsSrc.get(i).getQuantity()));
                    }
                }
                System.out.println("LIST QUANTITY OF PRODUCT WITH MARKET : " + superMarket.getName().toString() + " -> " + getQuantity(productsSrc));
            }
        });

        jButton_SendProduct.setText("SEND PRODUCT");
        jButton_SendProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SendProduct_ActionPerformed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void SendProduct_ActionPerformed(ActionEvent e) throws IOException, ParseException {
                readDataFromFileDst();
                saveDataInFile();
            }
        });

        close.setText("PREVIOUS");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close_ActionPerformed(e);
            }
        });

        jPanel2.setLayout(gridLayout4);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.add(show_Market, null);
        jPanel2.add(show_Displays, null);
        jPanel2.add(jComboBox_ListOfMarketDst, null);
        jPanel2.add(jComboBox_ListOfDisplays, null);
        jPanel2.add(show_ProductOfMarket, null);
        jPanel2.add(show_QuantityOfProduct, null);
        jPanel2.add(jButton_SendProduct, null);
        jPanel2.add(JTextField_ChoseQuantity, null);
        jPanel2.add(JTextField_Quantity, null);
        jPanel2.add(close, null);

        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel1.add(LesLables, null);
        LesLables.add(jLabel1, null);
        LesLables.add(jLabel2, null);
        LesLables.add(jLabel3, null);
        LesLables.add(jLabel4, null);
        LesLables.add(jLabel5, null);

        jPanel1.add(LesTextFields, null);
        LesTextFields.add(jComboBox_ListOfMarketDst, null);
        LesTextFields.add(jComboBox_ListOfDisplays, null);
        LesTextFields.add(jComboBox_ListProductOfMarket, null);
        LesTextFields.add(JTextField_Quantity, null);
        LesTextFields.add(JTextField_ChoseQuantity, null);
    }

    // LOSE
    public void Close_ActionPerformed(ActionEvent e) {
        productsSrc.removeAll(productsSrc);
        this.dispose();
    }

    //     Read text file with data from Market whose connected and add it in List
    @Override
    public void readDataFromFileSrc() throws ParseException {
        productsSrc.removeAll(productsSrc);
        try {
            File file = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                br = new BufferedReader(new FileReader("List Product of Market " + superMarket.getName().toString() + ".txt"));
                if (!br.ready()) {
                    throw new IOException();
                }
                if (productsSrc.isEmpty()) {
                    while ((lineSrc = br.readLine()) != null) {
                        //      index's of informations to get product in file and add it in List<Product>
                        int index_Libele1 = lineSrc.indexOf("Libele : ");
                        int index_Libele2 = lineSrc.indexOf("- Label");

                        int index_Label1 = lineSrc.indexOf("Label : ");
                        int index_Label2 = lineSrc.indexOf("- Type produit");

                        int index_TypeProduit1 = lineSrc.indexOf("Type produit : ");
                        int index_TypeProduit2 = lineSrc.indexOf("- Type etalage");

                        int index_TypeEtalage1 = lineSrc.indexOf("Type etalage : ");
                        int index_TypeEtalage2 = lineSrc.indexOf("- DDF");

                        int index_DDF1 = lineSrc.indexOf("DDF : ");
                        int index_DDF2 = lineSrc.indexOf("- DLC");

                        int index_DLC1 = lineSrc.indexOf("DLC : ");
                        int index_DLC2 = lineSrc.indexOf("- Quantity");

                        int index_Quantity1 = lineSrc.indexOf("Quantity : ");
                        int index_Quantity2 = lineSrc.indexOf(" - Price");

                        int index_Prix1 = lineSrc.indexOf("Price : ");
                        int length = lineSrc.length();

                        name_product = lineSrc.substring(index_Libele1 + 9, index_Libele2);
                        nameOfLabel = lineSrc.substring(index_Label1, index_Label2);
                        type_product = lineSrc.substring(index_TypeProduit1 + 15, index_TypeProduit2);
                        type_displays = lineSrc.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
                        DDF = format.parse(lineSrc.substring(index_DDF1 + 6, index_DDF2));
                        DLC = format.parse(lineSrc.substring(index_DLC1 + 6, index_DLC2));
                        quantity = Integer.parseInt(lineSrc.substring(index_Quantity1 + 11, index_Quantity2));
                        String c = String.valueOf(lineSrc.substring(lineSrc.indexOf("Price : "), length));
                        price = Float.valueOf(c.substring(8));
                        productsSrc.add(new Product(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price));
                    }
                }
                br.close();
            } else {
                System.out.println("No Products in this Market");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

//        productsSrc.forEach(a -> {
//            System.out.println("SRC : " + a.getName_product());
//        });
    }

    //     Read text file with data from Market whose receive product and add it in List
    @Override
    public void readDataFromFileDst() throws ParseException {
        productsDst.removeAll(productsDst);
        try {
            File file = new File("List Product of Market " + jComboBox_ListOfMarketDst.getSelectedItem().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                br = new BufferedReader(new FileReader("List Product of Market " + jComboBox_ListOfMarketDst.getSelectedItem().toString() + ".txt"));
                if (!br.ready()) {
                    throw new IOException();
                }
                if (productsDst.isEmpty()) {
                    while ((lineDst = br.readLine()) != null) {
                        //      index's of informations to get product in file and add it in List<Product>
                        int index_Libele1 = lineDst.indexOf("Libele : ");
                        int index_Libele2 = lineDst.indexOf("- Label");

                        int index_Label1 = lineDst.indexOf("Label : ");
                        int index_Label2 = lineDst.indexOf("- Type produit");

                        int index_TypeProduit1 = lineDst.indexOf("Type produit : ");
                        int index_TypeProduit2 = lineDst.indexOf("- Type etalage");

                        int index_TypeEtalage1 = lineDst.indexOf("Type etalage : ");
                        int index_TypeEtalage2 = lineDst.indexOf("- DDF");

                        int index_DDF1 = lineDst.indexOf("DDF : ");
                        int index_DDF2 = lineDst.indexOf("- DLC");

                        int index_DLC1 = lineDst.indexOf("DLC : ");
                        int index_DLC2 = lineDst.indexOf("- Quantity");

                        int index_Quantity1 = lineDst.indexOf("Quantity : ");
                        int index_Quantity2 = lineDst.indexOf(" - Price");

                        int index_Prix1 = lineDst.indexOf("Price : ");
                        int length = lineDst.length();

                        name_product = lineDst.substring(index_Libele1 + 9, index_Libele2);
                        nameOfLabel = lineDst.substring(index_Label1 + 8, index_Label2);
                        type_product = lineDst.substring(index_TypeProduit1 + 15, index_TypeProduit2);
                        type_displays = lineDst.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
                        DDF = format.parse(lineDst.substring(index_DDF1 + 6, index_DDF2));
                        DLC = format.parse(lineDst.substring(index_DLC1 + 6, index_DLC2));
                        quantity = Integer.parseInt(lineDst.substring(index_Quantity1 + 11, index_Quantity2));
                        String c = String.valueOf(lineDst.substring(lineDst.indexOf("Price : "), length));
                        price = Float.valueOf(c.substring(8));
                        productsDst.add(new Product(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price));
                    }
                }
                br.close();
            } else {
                System.out.println("No Products in this Market");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        productsDst.forEach(a -> {
//            System.out.println("DST : " + a.getName_product());
//        });
    }

    //      Update information in Stock of product Market Source and market destination
    @Override
    public void saveDataInFile() throws IOException, ParseException {
        try {
            File fileDest = new File("List Product of Market " + jComboBox_ListOfMarketDst.getSelectedItem().toString() + ".txt");
            File fileTempDest = new File("tempDest.txt");
            FileReader frDest = new FileReader(fileDest);
            BufferedReader brDest = new BufferedReader(frDest);

            File fileSrc = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            File fileTempSrc = new File("tempSrc.txt");
            FileReader frSrc = new FileReader(fileSrc);
            BufferedReader brSrc = new BufferedReader(frSrc);

//                productsSrc
//                productsDst
            productsDst.forEach(a -> {
                System.out.println("AVANT : " + a.getName_product() + " " + a.getQuantity());
            });

            Optional<Product> productToTrans = productsSrc
                    .stream()
                    .filter(x -> jComboBox_ListProductOfMarket.getSelectedItem().toString().equals(x.getName_product()))
                    .findFirst();
            Optional<Product> productInDest = productsDst
                    .stream()
                    .filter(x -> jComboBox_ListProductOfMarket.getSelectedItem().toString().equals(x.getName_product()))
                    .findFirst();

            if (JTextField_ChoseQuantity.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Veuillez choisir la quantité à transferer SVP !".toUpperCase(),
                        "MESSAGE ERROR",
                        JOptionPane.WARNING_MESSAGE);
                JTextField_ChoseQuantity.requestFocus();
            } else if (JTextField_ChoseQuantity.getText() != "") {
                final int q = Integer.parseInt(JTextField_ChoseQuantity.getText());
                productToTrans.ifPresent(x -> {
                    if (q > x.getQuantity()) {
                        JOptionPane.showMessageDialog(null, "Cette quantité ne peut etre transferer SVP !".toUpperCase(),
                                "MESSAGE ERROR",
                                JOptionPane.WARNING_MESSAGE);
                        JTextField_ChoseQuantity.requestFocus();
                    } else if (q < x.getQuantity()) {
                        productToTrans.ifPresent(y -> y.setQuantity(y.getQuantity() - q));
                        productToTrans.ifPresent(y -> y.notifyObservers());
                        productInDest.ifPresent(y -> y.setQuantity(y.getQuantity() + q));
                        productInDest.ifPresent(y -> y.notifyObservers());
//                        productInDest.ifPresent(y -> y.setDate_TRANSFER(date));
//                        productInDest.ifPresent(y -> y.setSuperMarkets(superMarket));
                        if (!productInDest.isPresent())
                            productToTrans.ifPresent(y -> {
                                Product product = new Product(y.getName_product(), y.getLabel(), y.getType_product(), y.getType_displays(),
                                        y.getDDF(), y.getDLC(), q, y.getPrice(), date, superMarket);
                                productsDst.add(product);
                            });

                    } else if (q == x.getQuantity()) {
                        productToTrans.ifPresent(y -> y.setQuantity(y.getQuantity() - q));
                        productToTrans.ifPresent(y -> y.notifyObservers());
                        productInDest.ifPresent(y -> y.setQuantity(y.getQuantity() + q));
                        productInDest.ifPresent(y -> y.notifyObservers());
                    }
                });

            }

            productsSrc.forEach(x -> {
                if (x.getQuantity() != 0) {
                    arrayListSrc.add("Libele : " + x.getName_product() + "- " +
                            "" + x.getLabel() + "- " +
                            "Type produit : " + x.getType_product() + "- " +
                            "Type etalage : " + x.getType_displays() + "- " +
                            "DDF : " + format.format(x.getDDF()) + " - " +
                            "DLC : " + format.format(x.getDLC()) + " - " +
                            "Quantity : " + x.getQuantity() + " - " +
                            "Price : " + x.getPrice());
                } else if (x.getQuantity() == 0) {
                    System.out.println(x.getName_product() + "is not available after this sale".toUpperCase());
                    JOptionPane.showMessageDialog(null, x.getName_product() + " is not available after this sale".toUpperCase(),
                            "INFORMATION MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    JTextField_ChoseQuantity.requestFocus();
                }
            });


            productsDst.forEach(a -> {
                System.out.println("APRES : " + a.getName_product() + " " + a.getQuantity());
            });

            productsDst.forEach(x -> {
                if (x.getQuantity() != 0) {
                    arrayListDest.add("Libele : " + x.getName_product() + "- " +
                            "Label : " + x.getLabel() + "- " +
                            "Type produit : " + x.getType_product() + "- " +
                            "Type etalage : " + x.getType_displays() + "- " +
                            "DDF : " + format.format(x.getDDF()) + " - " +
                            "DLC : " + format.format(x.getDLC()) + " - " +
                            "Quantity : " + x.getQuantity() + " - " +
                            "Price : " + x.getPrice());
                } else if (x.getQuantity() == 0) {
                    System.out.println(x.getName_product() + "is not available after this sale".toUpperCase());
                    JOptionPane.showMessageDialog(null, x.getName_product() + " is not available after this sale".toUpperCase(),
                            "INFORMATION MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    JTextField_ChoseQuantity.requestFocus();
                }
            });

            frDest.close();
            brDest.close();
            frSrc.close();
            brSrc.close();

            try {
                FileWriter fileReaderDest = new FileWriter(fileTempDest, false);
                BufferedWriter bufferedWriterDest = new BufferedWriter(fileReaderDest);
                for (int j = 0; j < arrayListDest.size(); j++) {
                    bufferedWriterDest.write(arrayListDest.get(j).toString());
                    bufferedWriterDest.newLine();
                }
                bufferedWriterDest.flush();
                bufferedWriterDest.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileWriter fileReaderSrc = new FileWriter(fileTempSrc, false);
                BufferedWriter bufferedWriterSrc = new BufferedWriter(fileReaderSrc);
                for (int j = 0; j < arrayListSrc.size(); j++) {
                    bufferedWriterSrc.write(arrayListSrc.get(j).toString());
                    bufferedWriterSrc.newLine();
                }
                bufferedWriterSrc.flush();
                bufferedWriterSrc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileSrc.renameTo(new File("poubelleSrc.txt"));
            fileSrc.delete();
            fileTempSrc.renameTo(new File("List Product of Market " + superMarket.getName().toString() + ".txt"));

            productsSrc.removeAll(productsSrc);
            arrayListSrc.removeAll(arrayListSrc);

            fileDest.renameTo(new File("poubelleDest.txt"));
            fileDest.delete();
            fileTempDest.renameTo(new File("List Product of Market " + jComboBox_ListOfMarketDst.getSelectedItem().toString() + ".txt"));
//            updateFileSrc();
            productsDst.removeAll(productsDst);
            arrayListDest.removeAll(arrayListDest);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void updateFileSrc() {
        try {
            File fileSrc = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            File fileTempSrc = new File("tempSrc.txt");
            FileReader frSrc = new FileReader(fileSrc);
            BufferedReader brSrc = new BufferedReader(frSrc);

            Optional<Product> productToTrans = productsSrc
                    .stream()
                    .filter(x -> jComboBox_ListProductOfMarket.getSelectedItem().toString().equals(x.getName_product()))
                    .findFirst();

            productsSrc.forEach(a -> {
                System.out.println("BEFORE : " + a.getName_product() + " " + a.getQuantity());
            });

            final int q = Integer.parseInt(JTextField_ChoseQuantity.getText());
            productToTrans.ifPresent(x -> {
                if (q < x.getQuantity()) {
                    productToTrans.ifPresent(y -> y.setQuantity(y.getQuantity() - q));
                    productToTrans.ifPresent(y -> y.notifyObservers());
//              if the chosen quantity is equal than of the product
                } else if (q == x.getQuantity()) {
                    productToTrans.ifPresent(y -> y.setQuantity(y.getQuantity() - q));
                    productToTrans.ifPresent(y -> y.notifyObservers());
                }
            });

            productsSrc.forEach(a -> {
                System.out.println("AFTER : " + a.getName_product() + " " + a.getQuantity());
            });

            productsSrc.forEach(x -> {
                if (x.getQuantity() != 0) {
                    arrayListSrc.add("Libele : " + x.getName_product() + "- " +
                            "" + x.getLabel() + "- " +
                            "Type produit : " + x.getType_product() + "- " +
                            "Type etalage : " + x.getType_displays() + "- " +
                            "DDF : " + format.format(x.getDDF()) + " - " +
                            "DLC : " + format.format(x.getDLC()) + " - " +
                            "Quantity : " + x.getQuantity() + " - " +
                            "Price : " + x.getPrice());
                } else if (x.getQuantity() == 0) {
                    System.out.println(x.getName_product() + "is not available after this sale".toUpperCase());
                    JOptionPane.showMessageDialog(null, x.getName_product() + " is not available after this sale".toUpperCase(),
                            "INFORMATION MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    JTextField_ChoseQuantity.requestFocus();
                }
            });

            frSrc.close();
            brSrc.close();

            try {
                FileWriter fileReaderSrc = new FileWriter(fileTempSrc, false);
                BufferedWriter bufferedWriterSrc = new BufferedWriter(fileReaderSrc);
                for (int j = 0; j < arrayListSrc.size(); j++) {
                    bufferedWriterSrc.write(arrayListSrc.get(j).toString());
                    bufferedWriterSrc.newLine();
                }
                bufferedWriterSrc.flush();
                bufferedWriterSrc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileSrc.renameTo(new File("poubelleSrc.txt"));
            fileSrc.delete();
            fileTempSrc.renameTo(new File("List Product of Market " + superMarket.getName().toString() + ".txt"));

            productsSrc.removeAll(productsSrc);
            arrayListSrc.removeAll(arrayListSrc);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}