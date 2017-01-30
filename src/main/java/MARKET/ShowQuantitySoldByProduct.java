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
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class ShowQuantitySoldByProduct extends JFrame implements Method {
    private List<Product> productsSrc = new ArrayList<>();
    private static List<Product> productSaleLastWeek = new ArrayList<>();
    private static List<Product> productsSaleLatWeek = new ArrayList<>();
    JButton close = new JButton();
    JButton show_ProductOfMarket = new JButton();
    JButton show_QuantityOfProduct = new JButton();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    BufferedReader reader;
    String line;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel LesTextFields = new JPanel();
    GridLayout gridLayout1 = new GridLayout(1, 2);
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    GridLayout gridLayout2 = new GridLayout(10, 1);
    GridLayout gridLayout3 = new GridLayout(10, 1);
    JComboBox jComboBox_ListProductOfMarket = new JComboBox();
    GridLayout gridLayout4 = new GridLayout();
    JPanel LesLables = new JPanel();
    JTextField JTextField_Quantity = new JTextField();
    private Date date_Sale;
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

    public ShowQuantitySoldByProduct() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T, S> Set<S> mapToSet(List<T> ls, Function<T, S> f) {
        return ls.stream()
                .map(f)
                .sorted()
                .collect(toSet());
    }

    public static Set<Integer> getQuantity(List<Product> productList) {
        Inter<Integer> i = a -> a.getQuantity();
        return mapProduct(productList, i);
    }

    public static Set<String> name_product(List<Product> products) {
        return mapToSet(products, Product::getName_product);
    }

    public static <T> Set<T> mapProduct(List<Product> ls, Inter<T> func) {
        Set<T> result = new TreeSet<>();
        for (Product a : ls)
            result.add(func.f(a));
        return result;
    }

    public static Float total_price(List<Product> products) {
        return products.stream()
                .map(a -> a.getPrice())
                .reduce((float) 0, Float::sum);
    }

    public static Set<Integer> getSumQuantitySold(List<Product> productList, String name) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        // Date du jour
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        c.add(Calendar.DATE, 6);
        Date end = c.getTime();
        productSaleLastWeek = productsSaleLatWeek
                .stream()
                .filter(x ->
                        x.getDate_SOLD()
                                .after(start)
                                &&
                                x.getDate_SOLD()
                                        .before(end)
                                && x.getName_product().equals(name))
                .collect(Collectors.toList());
        return productList.stream()
                .filter(x ->
                        x.getDate_SOLD()
                                .after(start)
                                &&
                                x.getDate_SOLD()
                                        .before(end)
                                && x.getName_product().equals(name))
//                .map(Product::getName_product)
                .map(Product::getQuantity)
                .collect(Collectors.toSet());
    }

    public static Set<String> getListOfPRoductSaleLastWeek(Stream<Product> productListStream, String jComboBox) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        // Date du jour
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        c.add(Calendar.DATE, 6);
        Date end = c.getTime();
//        JComboBox jComboBox_ListProductOfMarket = new JComboBox();
        System.out.println("LAST WEEK FROM " + format.format(start) + " TO THE " + format.format(end));
        productSaleLastWeek = productsSaleLatWeek
                .stream()
                .filter(x ->
                        x.getDate_SOLD()
                                .after(start)
                                &&
                                x.getDate_SOLD()
                                        .before(end)
                                && x.getName_product().equals(jComboBox))
                .collect(Collectors.toList());
        productSaleLastWeek.forEach(x -> {
            System.out.println(x.getName_product() + " -> DATE SOLD " + format.format(x.getDate_SOLD()) + " -> QUANTITY " + x.getQuantity());
        });
        return productListStream
                .filter(x ->
                        x.getDate_SOLD()
                                .after(start)
                                &&
                                x.getDate_SOLD()
                                        .before(end)
                                && x.getName_product().equals(jComboBox))
//                .map(Product::getName_product)
                .map(Product::getName_product)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private void jbInit() throws Exception {
        this.setResizable(true);
        this.setSize(new Dimension(950, 350));
        this.setTitle("       TRANSFERT PRODUCT        ");

        show_QuantityOfProduct.setEnabled(false);
        jPanel1.setLayout(gridLayout1);
        jLabel1.setText("     SELECT PRODUCT     ");
        jLabel2.setText("     QUANTITY     ");
        LesLables.setLayout(gridLayout2);
        LesTextFields.setLayout(gridLayout3);

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
                jComboBox_ListProductOfMarket.removeAllItems();
//                jComboBox_ListProductOfMarket.addItem(getListOfPRoductSaleLastWeek(productSaleLastWeek.stream()));

                readDataFromFileSrc();
                for (int i = 0; i < productsSrc.size(); i++) {
                    jComboBox_ListProductOfMarket.addItem(String.valueOf(productsSrc.get(i).getName_product()));
                }
                show_QuantityOfProduct.setEnabled(true);
//              Affichage de la liste des produits par PRIX saisie dans l'interface
//                System.out.println("PRODUCT LIST OF " + superMarket.getName().toString() + getName(productsSrc));
            }
        });


        show_QuantityOfProduct.setText("SHOW QUANTITY");
        show_QuantityOfProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ShowQuantity_ActionPerformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void ShowQuantity_ActionPerformed(ActionEvent e) throws ParseException {

                readDataFromFileDst();
                readDataFromFileSrc();
                JTextField_Quantity.setText(String.valueOf(getSumQuantitySold(productsSaleLatWeek, jComboBox_ListProductOfMarket.getSelectedItem().toString())));
                System.out.println("Product list sold last week : ".toUpperCase() + getListOfPRoductSaleLastWeek(productsSaleLatWeek.stream(), jComboBox_ListProductOfMarket.getSelectedItem().toString()));
            }
        });

        close.setText("PREVIOUS");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close(e);
            }

            private void close_ActionPerformed(ActionEvent e) {
            }
        });


        jPanel2.setLayout(gridLayout4);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

        jPanel2.add(show_ProductOfMarket, null);
        jPanel2.add(show_QuantityOfProduct, null);
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

        jPanel1.add(LesTextFields, null);
        LesTextFields.add(jComboBox_ListProductOfMarket, null);
        LesTextFields.add(JTextField_Quantity, null);
    }

    // close
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
                        type_displays = line.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
                        DDF = format.parse(line.substring(index_DDF1 + 6, index_DDF2));
                        DLC = format.parse(line.substring(index_DLC1 + 6, index_DLC2));
                        quantity = Integer.parseInt(line.substring(index_Quantity1 + 11, index_Quantity2));
                        String c = String.valueOf(line.substring(line.indexOf("Price : "), length));
                        price = Float.valueOf(c.substring(8));
                        productsSrc.add(new Product(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price));
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
    public void readDataFromFileDst() throws ParseException {
        productsSaleLatWeek.removeAll(productsSaleLatWeek);
        try {
            File file = new File("List Product SALE's of Market " + superMarket.getName().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                reader = new BufferedReader(new FileReader("List Product SALE's of Market " + superMarket.getName().toString() + ".txt"));
                if (!reader.ready()) {
                    throw new IOException();
                }
                if (productsSaleLatWeek.isEmpty()) {
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
                        int index_Prix2 = line.indexOf("- Date Sale");

                        int index_DateSale = line.indexOf("Date Sale : ");
                        int length = line.length();

                        name_product = line.substring(index_Libele1 + 9, index_Libele2);
                        nameOfLabel = line.substring(index_Label1 + 9, index_Label2);
                        type_product = line.substring(index_TypeProduit1 + 15, index_TypeProduit2);
                        type_displays = line.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
                        DDF = format.parse(line.substring(index_DDF1 + 6, index_DDF2));
                        DLC = format.parse(line.substring(index_DLC1 + 6, index_DLC2));
                        quantity = Integer.parseInt(line.substring(index_Quantity1 + 11, index_Quantity2));
                        String c = String.valueOf(line.substring(index_Prix1, index_Prix2));
                        price = Float.valueOf(c.substring(8));
                        String date = String.valueOf(line.substring(index_DateSale, length));
                        date_Sale = format.parse(date.substring(12));
                        productsSaleLatWeek.add(new Product(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price, date_Sale));
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

    @Override
    public void saveDataInFile() {

    }

}