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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class Show_Stock extends JFrame implements Method {
    private static List<Product> productsSaleLatWeek = new ArrayList<>();
    private static List<Product> productSaleLastWeek = new ArrayList<>();
    private static List<Product> productsSrc = new ArrayList<>();
    BufferedReader reader;
    String line;
    JButton close = new JButton();
    JButton show_ProductOfMarket = new JButton();
    JButton show_ProductSoldLastWeek = new JButton();
    JButton show_CA = new JButton();
    JButton show_SumQuantitySold = new JButton();
    JButton show_NumberSaleByProduct = new JButton();
    JButton show_QuantityOfProduct = new JButton();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout = new GridLayout();
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private Date date_Sale;
    private int quantity;
    private float price;
    private String type_product;
    private String type_displays;

    public Show_Stock() {
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
                .map(a -> a.getQuantity() * a.getPrice())
                .reduce((float) 0, Float::sum);
    }

    public static Map<String, Integer> getSumQuantitySold(List<Product> productList) {
        return productList.stream()
                .collect(Collectors
                        .groupingBy(Product::getName_product,
                                Collectors.summingInt(Product::getQuantity)));
    }

    public static Map<Float, Set<String>> getGroupingByPrice(List<Product> productList) {
        return productList.stream()
                .collect(Collectors
                        .groupingBy(Product::getPrice,
                                Collectors
                                        .mapping(Product::getName_product, Collectors.toSet())
                        )
                );
    }

    public static Map<String, Long> getNumberOfProductSold(Stream<Product> productStream) {
        return productStream
                .collect(Collectors
                        .groupingBy(Product::getName_product,
                                Collectors.counting()));
    }

    public static Set<String> getListOfPRoductSaleLastWeek(Stream<Product> productListStream) {
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
        System.out.println("LAST WEEK FROM " + format.format(start) + " TO THE " + format.format(end));
        productSaleLastWeek = productsSaleLatWeek
                .stream()
                .filter(x ->
                        x.getDate_SOLD()
                                .after(start)
                                &&
                                x.getDate_SOLD()
                                        .before(end))
                .collect(Collectors.toList());
//        productSaleLastWeek.forEach(x -> {
//            System.out.println(x.getName_product() + " -> " + format.format(x.getDate_SOLD()));
//        });
        return productListStream
                .filter(x ->
                        x.getDate_SOLD()
                                .after(start)
                                &&
                                x.getDate_SOLD()
                                        .before(end))
//                .map(Product::getName_product)
                .map(Product::getName_product)
                .collect(Collectors.toSet());
    }

    private void jbInit() throws Exception {
        this.setResizable(true);
        this.setSize(new Dimension(600, 200));
        this.setTitle("       SHOW STOCK        ");
//        SHOW_MARKET.setText("SHOW_MARKET");
//        SHOW_MARKET.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                Show_actionperformed(e);
//            }
//
//            private void Show_actionperformed(ActionEvent e) {
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
//                SHOW_MARKET.setEnabled(false);
//            }
//        });

        show_ProductOfMarket.setEnabled(true);
        show_QuantityOfProduct.setEnabled(false);
        show_ProductSoldLastWeek.setEnabled(false);
        show_SumQuantitySold.setEnabled(false);
        show_NumberSaleByProduct.setEnabled(false);
        show_CA.setEnabled(false);

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
                readDataFromFileSrc();
                System.out.println(Product.name_product(productsSrc));
                show_QuantityOfProduct.setEnabled(true);

            }
        });

        show_QuantityOfProduct.setText("SHOW QUANTITY");
        show_QuantityOfProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Show_actionperformed(e);
            }

            private void Show_actionperformed(ActionEvent e) {
                System.out.println(getQuantity(productsSrc));
                show_ProductSoldLastWeek.setEnabled(true);
            }
        });

        show_ProductSoldLastWeek.setText("SHOW LIST PRODUCT SOLD LAST WEEK");
        show_ProductSoldLastWeek.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_actionperformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Show_actionperformed(ActionEvent e) throws ParseException {
                readDataFromFileDst();
                readDataFromFileSrc();
                System.out.println("Product list sold last week : ".toUpperCase() + getListOfPRoductSaleLastWeek(productsSaleLatWeek.stream()));
                show_SumQuantitySold.setEnabled(true);
            }
        });


        show_NumberSaleByProduct.setText("NUMBER SALE BY PRODUCT");
        show_NumberSaleByProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_actionperformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Show_actionperformed(ActionEvent e) throws ParseException {
                System.out.println("Number of sale by product -> ".toUpperCase() + getNumberOfProductSold(productSaleLastWeek.stream()));
                show_CA.setEnabled(true);
            }
        });

        show_SumQuantitySold.setText("SUM OF QUANTITY SOLD LAST WEEK");
        show_SumQuantitySold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_actionperformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Show_actionperformed(ActionEvent e) throws ParseException {
                System.out.println("The sum of the quantities sold -> ".toUpperCase() + getSumQuantitySold(productSaleLastWeek));
                show_NumberSaleByProduct.setEnabled(true);
            }
        });

        show_CA.setText("TURNOVER " + "'CA'");
        show_CA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Show_actionperformed(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Show_actionperformed(ActionEvent e) throws ParseException {
                System.out.println("Last week's turnover (CA) : ".toUpperCase() + total_price(productsSaleLatWeek));
            }
        });

        close.setText("PREVIOUS");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close(e);
            }
        });
        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jPanel1.setLayout(gridLayout);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(close, null);
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(show_ProductOfMarket, null);
        jPanel2.add(show_QuantityOfProduct, null);
        jPanel2.add(show_ProductSoldLastWeek, null);
        jPanel2.add(show_SumQuantitySold, null);
        jPanel2.add(show_NumberSaleByProduct, null);
        jPanel2.add(show_CA, null);
        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
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
                        nameOfLabel = line.substring(index_Label1 + 8, index_Label2);
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
                        nameOfLabel = line.substring(index_Label1 + 8, index_Label2);
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


