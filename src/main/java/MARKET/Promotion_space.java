package MARKET;

import AUTH.Auth_MARKET;
import Model.Method;
import Model.Product;
import Model.SuperMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by slima_000 on 13/11/2016.
 */
public class Promotion_space extends JFrame implements Method {
    private static List<Product> productsSrc = new ArrayList<>();
    private static List<Promotion> promotion_products = new ArrayList<>();
    private static List arrayList = new ArrayList<>();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    JButton jButton_AddPromotion = new JButton();
    JButton jButton_CLOSE = new JButton();
    GridLayout gridLayout2 = new GridLayout();
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private Date PromotionDate;
    private int quantity;
    private float price;
    private String type_product;
    private String type_displays;
    private BufferedReader reader;
    private String lineDst;
    private String lineSrc;
    private BufferedWriter bufWriter;
    private BufferedReader bufferedReader;

    public Promotion_space() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(true);
        this.setSize(new Dimension(350, 350));
        this.setTitle("PROMOTION SPACE ");

        jButton_AddPromotion.setText("ENTER PROMOTION");
        jButton_AddPromotion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    EnterPromotion(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            private void EnterPromotion(ActionEvent e) throws ParseException, IOException {
                readDataFromFileSrc();
                readDataFromFileDst();
                addPromotion_Product();
            }
        });

        jButton_CLOSE.setText("PREVIOUS");
        jButton_CLOSE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CLOSE_actionPerformed(e);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));
        jPanel1.setLayout(gridLayout2);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton_CLOSE, null);
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jButton_AddPromotion, null);
        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
    }

    private void addPromotion_Product() throws IOException {
        Date date = new Date();
        long theFuture = System.currentTimeMillis() + (86400 * 7 * 1000);
        Date nextWeek = new Date(theFuture);

        List<Product> productToPromotion = productsSrc
                .stream()
                .filter(x -> x.getDLC().compareTo(date) > 0 && x.getDLC().compareTo(nextWeek) < 0)
                .collect(Collectors.toList());

        System.out.println("LIST OF PRODUCT IN STOCK");
        productsSrc.forEach(x -> {
            System.out.println("Product : " + x.getName_product() + " -> " + x.getPrice());
        });

        float input = Float.parseFloat(JOptionPane.showInputDialog(null, "CHOSE NUMBER BETWEEN 0 AND 1 TO REDUCE PRICE"));

        if (input > 1.0) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir entre 0.0 et 1.0 !".toUpperCase(),
                    "MESSAGE ERROR",
                    JOptionPane.WARNING_MESSAGE);
        } else if (input <= 1.0) {
            productToPromotion.forEach(y -> {
//                promotion_products.forEach(x -> {
//                    if (x.getPrice() == (y.setPrice((float) (y.getPrice() - (y.getPrice() * input))))) {
//                        y.setQuantity(y.getQuantity() + x.getQuantity());
//                        Promotion product = new Promotion(y.getName_product(), y.getLabel(), y.getType_product(), y.getType_displays(),
//                                y.getDDF(), y.getDLC(), y.getQuantity(), y.setPrice((float) (y.getPrice() - (y.getPrice() * input))), date, superMarket);
//                        promotion_products.add(product);
//                    } else if (x.getPrice() != (y.setPrice((float) (y.getPrice() - (y.getPrice() * input))))) {
                Promotion product = new Promotion(y.getName_product(), y.getLabel(), y.getType_product(), y.getType_displays(),
                        y.getDDF(), y.getDLC(), y.getQuantity(), y.setPrice((float) (y.getPrice() - (y.getPrice() * input))), date, superMarket);
                promotion_products.add(product);
//                    }
//                });
//            reduce price with 15% of original price
//            System.out.println((float) (y.getPrice()));
//            System.out.println((float) (y.getPrice() - (y.getPrice() * Float.valueOf(input))));

            });
        }

        if (promotion_products.isEmpty()) {
            System.out.println("No Product in promotion".toUpperCase());
        } else if (!promotion_products.isEmpty()) {
            System.out.println("LIST OF PRODUCT IN PROMOTION");
            promotion_products.forEach(x -> {
                System.out.println("Product : " + x.getName_product()
                        + " -> " +
                        " DLC : " + format.format(x.getDLC())
                        + " PRICE -> " + x.getPrice());
            });
            saveDataInFile();
        }
    }

    public void CLOSE_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    //     Read text file with data from Market and add it in List
    @Override
    public void readDataFromFileSrc() throws ParseException {
        productsSrc.removeAll(productsSrc);
        try {
            File file = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                bufferedReader = new BufferedReader(new FileReader("List Product of Market " + superMarket.getName().toString() + ".txt"));
                if (!bufferedReader.ready()) {
                    throw new IOException();
                }
                if (productsSrc.isEmpty()) {
                    while ((lineSrc = bufferedReader.readLine()) != null) {
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
                bufferedReader.close();
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

    @Override
    public void readDataFromFileDst() throws ParseException {
//        promotion_products.removeAll(promotion_products);
//        try {
//            File file = new File("List Of Promotion " + superMarket.getName().toString() + ".txt");
//            if (file.exists() && file.length() != 0) {
//                bufferedReader = new BufferedReader(new FileReader("List Of Promotion " + superMarket.getName().toString() + ".txt"));
//                if (!bufferedReader.ready()) {
//                    throw new IOException();
//                }
//                if (promotion_products.isEmpty()) {
//                    while ((lineDst = bufferedReader.readLine()) != null) {
//                        //      index's of informations to get product in file and add it in List<Product>
//                        int index_Libele1 = lineDst.indexOf("Libele : ");
//                        int index_Libele2 = lineDst.indexOf("- Label");
//
//                        int index_Label1 = lineDst.indexOf("Label : ");
//                        int index_Label2 = lineDst.indexOf("- Type produit");
//
//                        int index_TypeProduit1 = lineDst.indexOf("Type produit : ");
//                        int index_TypeProduit2 = lineDst.indexOf("- Type etalage");
//
//                        int index_TypeEtalage1 = lineDst.indexOf("Type etalage : ");
//                        int index_TypeEtalage2 = lineDst.indexOf("- DDF");
//
//                        int index_DDF1 = lineDst.indexOf("DDF : ");
//                        int index_DDF2 = lineDst.indexOf("- DLC");
//
//                        int index_DLC1 = lineDst.indexOf("DLC : ");
//                        int index_DLC2 = lineDst.indexOf("- Quantity");
//
//                        int index_Quantity1 = lineDst.indexOf("Quantity : ");
//                        int index_Quantity2 = lineDst.indexOf(" - Price");
//
//                        int index_Prix1 = lineDst.indexOf("Price : ");
//                        int index_Prix2 = lineDst.indexOf("- Promotion Date");
//
//                        int index_Promo1 = lineDst.indexOf("Promotion Date : ");
//                        int index_Promo2 = lineDst.indexOf("- Market");
//
//
//                        name_product = lineDst.substring(index_Libele1 + 9, index_Libele2);
//                        nameOfLabel = lineDst.substring(index_Label1 + 8, index_Label2);
//                        type_product = lineDst.substring(index_TypeProduit1 + 15, index_TypeProduit2);
//                        type_displays = lineDst.substring(index_TypeEtalage1 + 15, index_TypeEtalage2);
//                        DDF = format.parse(lineDst.substring(index_DDF1 + 6, index_DDF2));
//                        DLC = format.parse(lineDst.substring(index_DLC1 + 6, index_DLC2));
//                        quantity = Integer.parseInt(lineDst.substring(index_Quantity1 + 11, index_Quantity2));
//                        String c = String.valueOf(lineDst.substring(index_Prix1, index_Prix2));
//                        price = Float.valueOf(c.substring(8));
//                        PromotionDate = format.parse(lineDst.substring(index_Promo1 + 17, index_Promo2 - 1));
//
//                        promotion_products.add(new Promotion(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price, PromotionDate, superMarket));
//                    }
//                }
//                bufferedReader.close();
//            } else {
//                System.out.println("No Products in this Market");
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
    }

    @Override
    public void saveDataInFile() throws IOException {
        arrayList.removeAll(arrayList);
        Date date = new Date();
        try {
            File fileSrc = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            File fileTempDest = new File("tempDest.txt");
            FileReader frDest = new FileReader(fileSrc);
            BufferedReader brDest = new BufferedReader(frDest);

            arrayList.removeAll(arrayList);
//          add product in promotion with market
            promotion_products.forEach(x -> {
                arrayList.add("Libele : " + x.getName_product() + "- " +
                        "Label : " + x.getLabel() + "- " +
                        "Type produit : " + x.getType_product() + "- " +
                        "Type etalage : " + x.getType_displays() + "- " +
                        "DDF : " + format.format(x.getDDF()) + " - " +
                        "DLC : " + format.format(x.getDLC()) + " - " +
                        "Quantity : " + x.getQuantity() + " - " +
                        "Price : " + x.getPrice() + " - " +
                        "Promotion Date : " + format.format(date) + " - " +
                        "Market : " + superMarket.getName());
            });
//            write in file with product in promotion with market

            frDest.close();
            brDest.close();

            try {
                FileWriter fileWriter = new FileWriter("List Of Promotion " + superMarket.getName() + ".txt", true);
                bufWriter = new BufferedWriter(fileWriter);
                for (int i = 0; i < arrayList.size(); i++) {
                    bufWriter.write(arrayList.get(i).toString());
                    bufWriter.newLine();
                }

                bufWriter.close();
//              update stock of market who sale product

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            fileTempDest.renameTo(new File("List Of Promotion " + superMarket.getName() + ".txt"));

            promotion_products.removeAll(promotion_products);
            arrayList.removeAll(arrayList);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
