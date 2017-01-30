package MARKET;

import AUTH.Auth_MARKET;
import Model.Method;
import Model.Product;
import Model.SuperMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class SALE_PRODUCT extends JFrame implements Method {
    private static List arrayListSrc = new ArrayList<>();
    private static List arrayList = new ArrayList<>();
    //    private static List<Product> productsDst = new ArrayList<>();
    private static List<Product> productsSrc = new ArrayList<>();
    //    BUTTON
    JButton jButton_CLOSE = new JButton();
    JButton jButton_SHOW_ProductOfMarket = new JButton();
    JButton jButton_SHOW_ProductInformation = new JButton();
    JButton jButton_SALEPRODUCT = new JButton();
    //   DATE FORMAT
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    //   PANEL
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel LesTextFields = new JPanel();
    JPanel LesLables = new JPanel();
    //    LABEL
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    //  GRIDLAYOUT
    GridLayout gridLayout1 = new GridLayout(1, 2);
    GridLayout gridLayout2 = new GridLayout(10, 1);
    GridLayout gridLayout3 = new GridLayout(10, 1);
    GridLayout gridLayout4 = new GridLayout();
    //    TEXTFIELD
    JTextField JTextField_Label = new JTextField();
    JTextField JTextField_TypeProduct = new JTextField();
    JTextField JTextField_TypeDisplays = new JTextField();
    JTextField JTextField_DDF = new JTextField();
    JTextField JTextField_DLC = new JTextField();
    JTextField JTextField_Quantity = new JTextField();
    JTextField JTextField_Price = new JTextField();
    JTextField JTextField_ChoseQuantity = new JTextField();
    JComboBox jComboBox_ListProductOfMarket = new JComboBox();


    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);
    private BufferedWriter bufWriter;
    private String lineSrc;
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private int quantity;
    private float price;
    private String type_product;
    private String type_displays;
    private BufferedReader br;

    public SALE_PRODUCT() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(true);
        this.setSize(new Dimension(950, 350));
        this.setTitle("       SALE PRODUCT        ");
        jButton_SHOW_ProductInformation.setEnabled(false);

        JTextField_Quantity.setEditable(false);
        JTextField_Label.setEditable(false);
        JTextField_TypeProduct.setEditable(false);
        JTextField_TypeDisplays.setEditable(false);
        JTextField_DDF.setEditable(false);
        JTextField_DLC.setEditable(false);
        JTextField_Price.setEditable(false);
        JTextField_ChoseQuantity.setEditable(true);
        jButton_SALEPRODUCT.setEnabled(false);

        jPanel1.setLayout(gridLayout1);
        jLabel1.setText("     SELECT PRODUCT     ");
        jLabel2.setText("     LABEL     ");
        jLabel3.setText("     TYPE PRODUCT     ");
        jLabel4.setText("     TYPE DISPLAYS     ");
        jLabel5.setText("     DDF     ");
        jLabel6.setText("     DLC     ");
        jLabel7.setText("     PRICE     ");
        jLabel8.setText("     QUANTITY     ");
        jLabel9.setText("     CHOSE QUANTITY TO SALE      ");
        LesLables.setLayout(gridLayout2);
        LesTextFields.setLayout(gridLayout3);

        jButton_SHOW_ProductOfMarket.setText("SHOW PRODUCT");
        jButton_SHOW_ProductOfMarket.addActionListener(new ActionListener() {
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
                readDataFromFileSrc();
                for (int i = 0; i < productsSrc.size(); i++) {
                    jComboBox_ListProductOfMarket.addItem(String.valueOf(productsSrc.get(i).getName_product()));
                }
                jButton_SHOW_ProductInformation.setEnabled(true);
            }
        });

        jButton_SHOW_ProductInformation.setText("SHOW INFORMATION OF PRODUCT");
        jButton_SHOW_ProductInformation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Show_actionperformed(e);
            }

            private void Show_actionperformed(ActionEvent e) {
                jButton_SALEPRODUCT.setEnabled(true);
                for (int i = 0; i < productsSrc.size(); i++) {
                    if (jComboBox_ListProductOfMarket.getSelectedItem().equals(productsSrc.get(i).getName_product())) {
                        JTextField_Quantity.setText(String.valueOf(productsSrc.get(i).getQuantity()));
                        JTextField_Label.setText(String.valueOf(productsSrc.get(i).getLabel()));
                        JTextField_TypeProduct.setText(String.valueOf(productsSrc.get(i).getType_product()));
                        JTextField_TypeDisplays.setText(String.valueOf(productsSrc.get(i).getType_displays()));
                        JTextField_DDF.setText(format.format(productsSrc.get(i).getDDF()));
                        JTextField_DLC.setText(format.format(productsSrc.get(i).getDDF()));
                        JTextField_Quantity.setText(String.valueOf(productsSrc.get(i).getQuantity()));
                        JTextField_Price.setText(String.valueOf(productsSrc.get(i).getPrice()));
                    }
                }
//                System.out.println("LIST QUANTITY OF PRODUCT WITH MARKET : " + jComboBox_ListOfMarketSrc.getSelectedItem().toString() + " -> " + getQuantity(productsSrc));
            }
        });

        jButton_SALEPRODUCT.setText("SALE PRODUCT");
        jButton_SALEPRODUCT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Sale_ActionPerformed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            private void Sale_ActionPerformed(ActionEvent e) throws IOException, ParseException {
                readDataFromFileSrc();
                saveDataInFile();
            }
        });

        jButton_CLOSE.setText("PREVIOUS");
        jButton_CLOSE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close(e);
            }
        });

        jPanel2.setLayout(gridLayout4);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

        jPanel2.add(jButton_SHOW_ProductOfMarket, null);
        jPanel2.add(JTextField_TypeProduct, null);
        jPanel2.add(JTextField_TypeDisplays, null);
        jPanel2.add(JTextField_DDF, null);
        jPanel2.add(JTextField_DLC, null);
        jPanel2.add(JTextField_Price, null);
        jPanel2.add(JTextField_Quantity, null);
        jPanel2.add(JTextField_ChoseQuantity, null);
        jPanel2.add(jButton_SHOW_ProductInformation, null);
        jPanel2.add(jButton_SALEPRODUCT, null);
        jPanel2.add(jButton_CLOSE, null);

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
        LesLables.add(jLabel6, null);
        LesLables.add(jLabel7, null);
        LesLables.add(jLabel8, null);
        LesLables.add(jLabel9, null);

        jPanel1.add(LesTextFields, null);
        LesTextFields.add(jComboBox_ListProductOfMarket, null);
        LesTextFields.add(JTextField_Label, null);
        LesTextFields.add(JTextField_TypeProduct, null);
        LesTextFields.add(JTextField_TypeDisplays, null);
        LesTextFields.add(JTextField_DDF, null);
        LesTextFields.add(JTextField_DLC, null);
        LesTextFields.add(JTextField_Price, null);
        LesTextFields.add(JTextField_Quantity, null);
        LesTextFields.add(JTextField_ChoseQuantity, null);
    }

    // jButton_CLOSE
    public void Close(ActionEvent e) {
        productsSrc.removeAll(productsSrc);
        this.dispose();
    }
    //     Read text file with data from Market and add it in List
    @Override
    public void readDataFromFileSrc() throws ParseException {
        productsSrc.removeAll(productsSrc);
        try {
            File file = new File("List Product of Market " + superMarket.getName() + ".txt");
            if (file.exists() && file.length() != 0) {
                br = new BufferedReader(new FileReader("List Product of Market " + superMarket.getName() + ".txt"));
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
                        nameOfLabel = lineSrc.substring(index_Label1 + 8, index_Label2);
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
    }
    @Override
    public void readDataFromFileDst() throws ParseException {

    }
    @Override
    public void saveDataInFile() throws IOException {
        arrayList.removeAll(arrayList);
        Date date = new Date();
        if (JTextField_ChoseQuantity.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir la quantité à transferer SVP !".toUpperCase(),
                    "WARNING MESSAGE",
                    JOptionPane.WARNING_MESSAGE);
            JTextField_ChoseQuantity.requestFocus();
        } else if (JTextField_ChoseQuantity.getText() != "") {
//          add product sale with market who sale to file of product Sale
            arrayList.add("Libele : " + jComboBox_ListProductOfMarket.getSelectedItem().toString() + "- " +
                    "Label : " + JTextField_Label.getText().toString() + "- " +
                    "Type produit : " + JTextField_TypeProduct.getText().toString() + "- " +
                    "Type etalage : " + JTextField_TypeDisplays.getText().toString() + "- " +
                    "DDF : " + JTextField_DDF.getText().toString() + " - " +
                    "DLC : " + JTextField_DLC.getText().toString() + " - " +
                    "Quantity : " + JTextField_ChoseQuantity.getText().toString() + " - " +
                    "Price : " + JTextField_Price.getText().toString() + " - " +
                    "Date Sale : " + format.format(date));
//            write in file with product sale with market
            try {
                FileWriter fileWriter = new FileWriter("List Product SALE'S of Market " + superMarket.getName() + ".txt", true);
                bufWriter = new BufferedWriter(fileWriter);
                for (int i = 0; i < arrayList.size(); i++) {
                    bufWriter.write(arrayList.get(i).toString());
                }
                bufWriter.newLine();
                bufWriter.close();
//              update stock of market who sale product
                updateFileSrc();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
//          Reset
            arrayList.removeAll(arrayList);
//          close window After treatment
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void updateFileSrc() {
        try {
//          read stock with a market
            File fileSrc = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
//          creat file where save new information
            File fileTempSrc = new File("tempSrc.txt");
            FileReader frSrc = new FileReader(fileSrc);
            BufferedReader brSrc = new BufferedReader(frSrc);
//          Optional allows me to boost my methods and indicate that my method is likely to return "empty" values
            Optional<Product> productToTrans = productsSrc
                    .stream()
                    .filter(x -> jComboBox_ListProductOfMarket.getSelectedItem().toString().equals(x.getName_product()))
                    .findFirst();
//          show stock before treatment
            productsSrc.forEach(a -> {
                System.out.println("BEFORE : " + a.getName_product() + " " + a.getQuantity());
            });
//          "q" is declared as final in order to be used in the optional
            final int q = Integer.parseInt(JTextField_ChoseQuantity.getText());
            productToTrans.ifPresent(x -> {
//              Check that the chosen quantity is greater than that of the product
                if (q > x.getQuantity()) {
                    JOptionPane.showMessageDialog(null, "Cette quantité ne peut etre vendu SVP !".toUpperCase(),
                            "WARNING MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                    JTextField_ChoseQuantity.requestFocus();
//              Check that the chosen quantity is smaller than that of the product
                } else if (q < x.getQuantity()) {
                    productToTrans.ifPresent(y -> y.setQuantity(y.getQuantity() - q));
                    productToTrans.ifPresent(y -> y.notifyObservers());
//              if the chosen quantity is equal than of the product
                } else if (q == x.getQuantity()) {
                    productToTrans.ifPresent(y -> y.setQuantity(y.getQuantity() - q));
                    productToTrans.ifPresent(y -> y.notifyObservers());
                }
            });
//          show stock after treatment
            productsSrc.forEach(a -> {
                System.out.println("AFTER : " + a.getName_product() + " " + a.getQuantity());
            });
//          add product to File after treatment
            productsSrc.forEach(x -> {
                if (x.getQuantity() != 0) {
                    arrayListSrc.add("Libele : " + x.getName_product() + "- " +
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

            frSrc.close();
            brSrc.close();
//          write new data
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
//          delete older file
            fileSrc.renameTo(new File("poubelleSrc.txt"));
            fileSrc.delete();
//          rename file where data is temporarily saved
            fileTempSrc.renameTo(new File("List Product of Market " + superMarket.getName().toString() + ".txt"));
            productsSrc.removeAll(productsSrc);
            arrayListSrc.removeAll(arrayListSrc);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}


