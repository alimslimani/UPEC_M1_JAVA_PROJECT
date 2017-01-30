package MARKET;

import AUTH.Auth_MARKET;
import Model.Inter;
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
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by slima_000 on 12/11/2016.
 */
public class Show_StockByDisplays extends JFrame implements Method {
    private static List<Product> productsSrc = new ArrayList<>();
    private static JTextField JTextField_Price = new JTextField();
    BufferedReader reader;
    String line;
    JButton close = new JButton();
    JButton show_displays = new JButton();
    JButton show_ProductOfMarket = new JButton();
    JButton show_QuantityOfProduct = new JButton();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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
    JTextField JTextField_PricePr = new JTextField();
    JComboBox jComboBox_ListOfDisplays = new JComboBox();
    JComboBox jComboBox_ListProductOfMarket = new JComboBox();
    GridLayout gridLayout4 = new GridLayout();
    JPanel LesLables = new JPanel();
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private int quantity;
    private float price;
    private String type_product;
    private String type_etalage;
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);

    public Show_StockByDisplays() {
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

    public static Set<String> getNameOfProduct(List<Product> productList) {
        Inter<String> i = a -> a.getName_product();
        return mapProduct(productList, i);
    }

    public static Set<Float> getPrice(List<Product> productList) {
        Inter<Float> i = a -> a.getPrice();
        return mapProduct(productList, i);
    }

    public static Set<String> getListProductByPrice(List<Product> productStream) {
        return productStream.stream()
                .filter(a -> a.getPrice() > Float.valueOf(JTextField_Price.getText().toString()))
                .map(Product::getName_product)
                .collect(Collectors.toSet());
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(700, 350));
        this.setTitle("       SHOW STOCK        ");

        JOptionPane.showMessageDialog(null, "Veuillez choisir le prix du filtre avant la recherche !".toUpperCase(),
                "MESSAGE ERROR",
                JOptionPane.INFORMATION_MESSAGE);

        show_ProductOfMarket.setEnabled(false);
        show_displays.setEnabled(true);
        show_QuantityOfProduct.setEnabled(false);

        JTextField_Quantity.setEditable(false);
        JTextField_PricePr.setEditable(false);

        jPanel1.setLayout(gridLayout1);

        jLabel1.setText("     CHOSE PRICE TO FILTER     ");
        jLabel2.setText("     DISPLAYS      ");
        jLabel3.setText("     SELECT PRODUCT     ");
        jLabel4.setText("     QUANTITY     ");
        jLabel5.setText("     PRICE       ");
        LesLables.setLayout(gridLayout2);
        LesTextFields.setLayout(gridLayout3);
//        SHOW_MARKET.setText("SHOW MARKET");
//        SHOW_MARKET.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                Show_actionperformed(e);
//            }
//
//            private void Show_actionperformed(ActionEvent e) {
//                JTextField_Quantity.removeAll();
//                JTextField_Price.removeAll();
//                jComboBox_ListOfMarket.removeAllItems();
//                try {
//                    //Appel la methode ConnectBase de ConnectDB pour faire la requete si le login et le MDP exite
//                    connection = ConnexionPG.ConnexionPG();
//                    statement = connection.createStatement();
//                    //utilisation de PreparedStatement afin d'eviter les injection SQL
//                    //pour securiser ll'acces aux donnees
//                    PreparedStatement select_market = connection.prepareStatement("select login from market");
//                    //Les champs de la methode qui se remplissent lors de la saisie
//                    //Execution de la requete
//                    get_Market = select_market.executeQuery();
//                    while (get_Market.next()) {
//                        connected = true;
//                        getLogin = get_Market.getString(1);
//                        jComboBox_ListOfMarket.addItem(getLogin);
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                show_displays.setEnabled(true);
//            }
//        });
        show_displays.setText("SHOW DISPLAYS");
        show_displays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_actionperformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Show_actionperformed(ActionEvent e) throws ParseException {
                readDataFromFileSrc();
                jComboBox_ListOfDisplays.removeAllItems();
                JTextField_Quantity.removeAll();
                JTextField_Price.removeAll();
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
                    Show_actionperformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Show_actionperformed(ActionEvent e) throws ParseException {
                productsSrc.removeAll(productsSrc);
                jComboBox_ListProductOfMarket.removeAllItems();
                JTextField_Quantity.removeAll();
                JTextField_Price.removeAll();
                readDataFromFileSrc();
                for (int i = 0; i < productsSrc.size(); i++) {
                    if (jComboBox_ListOfDisplays.getSelectedItem().equals(productsSrc.get(i).getType_displays())) {
                        jComboBox_ListProductOfMarket.addItem(String.valueOf(productsSrc.get(i).getName_product()));
                    }
                }
                show_QuantityOfProduct.setEnabled(true);
//              Affichage de la liste des produits par PRIX saisie dans l'interface
                System.out.println("PRODUCT LIST PRICE OF " +
                        superMarket.getName().toString() +
                        " MORE THAN : 30 " +
                        " -> " + Product.priceSupThirty(productsSrc));

                System.out.println("PRODUCT LIST PRICE OF " +
                        superMarket.getName().toString() +
                        " MORE THAN : " +
                        JTextField_Price.getText().toString() +
                        " -> " + getListProductByPrice(productsSrc));
            }
        });

        show_QuantityOfProduct.setText("SHOW QUANTITY");
        show_QuantityOfProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Show_actionperformed(e);
            }

            private void Show_actionperformed(ActionEvent e) {
                for (int i = 0; i < productsSrc.size(); i++) {
                    if (jComboBox_ListProductOfMarket.getSelectedItem().equals(productsSrc.get(i).getName_product())) {
                        JTextField_Quantity.setText(String.valueOf(productsSrc.get(i).getQuantity()));
                        JTextField_PricePr.setText(String.valueOf(productsSrc.get(i).getPrice()));
                    }
                }
                System.out.println("LIST NAME OF PRODUCT WITH MARKET : " + superMarket.getName().toString() + " -> " + getNameOfProduct(productsSrc));
                System.out.println("LIST QUANTITY OF PRODUCT WITH MARKET : " + superMarket.getName().toString() + " -> " + getQuantity(productsSrc));
                System.out.println("LIST PRICE OF PRODUCT WITH MARKET : " + superMarket.getName().toString() + " -> " + getPrice(productsSrc));
            }
        });

        close.setText("PREVIOUS");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close(e);
            }
        });

        jPanel2.setLayout(gridLayout4);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

        jPanel2.add(show_displays, null);
        jPanel2.add(jComboBox_ListOfDisplays, null);
        jPanel2.add(show_ProductOfMarket, null);
        jPanel2.add(JTextField_Price, null);
        jPanel2.add(show_QuantityOfProduct, null);
        jPanel2.add(JTextField_PricePr, null);
        jPanel2.add(JTextField_Quantity, null);
        jPanel2.add(close, null);

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
        LesLables.add(jLabel5, null);

        jPanel1.add(LesTextFields, null);
        LesTextFields.add(JTextField_Price, null);
        LesTextFields.add(jComboBox_ListOfDisplays, null);
        LesTextFields.add(jComboBox_ListProductOfMarket, null);
        LesTextFields.add(JTextField_Quantity, null);
        LesTextFields.add(JTextField_PricePr, null);
    }

    // CLOSE
    public void Close(ActionEvent e) {
        productsSrc.removeAll(productsSrc);
        this.dispose();
    }

    //     Read text file with data from Market and add it in List
    @Override
    public void readDataFromFileSrc() throws ParseException {
        productsSrc.removeAll(productsSrc);
        try {
            File file = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                reader = new BufferedReader(new FileReader("List Product of Market " + superMarket.getName().toString() + ".txt"));
                if (!reader.ready()) {
                    throw new IOException();
                }
                if (productsSrc.isEmpty()) {
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
                        nameOfLabel = line.substring(index_Label1 + 9, index_Label2);
                        type_product = line.substring(index_TypeProduit1 + 15, index_TypeProduit2);
                        type_etalage = line.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
                        DDF = format.parse(line.substring(index_DDF1 + 6, index_DDF2));
                        DLC = format.parse(line.substring(index_DLC1 + 6, index_DLC2));
                        quantity = Integer.parseInt(line.substring(index_Quantity1 + 11, index_Quantity2));
                        String c = String.valueOf(line.substring(line.indexOf("Price : "), length));
                        price = Float.valueOf(c.substring(8));
                        productsSrc.add(new Product(name_product, nameOfLabel, type_product, type_etalage, DDF, DLC, quantity, price));
//                    }
                    }
                }
                reader.close();
            } else {
                System.out.println("No Products in this Market");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //     Read text file with data from Market whose receive product and add it in List
    @Override
    public void readDataFromFileDst() {

    }

    @Override
    public void saveDataInFile() {

    }

}


