package MARKET;

import AUTH.Auth_MARKET;
import Model.*;
import Model.Label;
import com.toedter.calendar.JDateChooser;

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

/**
 * Created by slima_000 on 12/11/2016.
 */
public class AddProduct extends JFrame implements Method {
    private static List<Product> products = new ArrayList<>();
    private static List<Product> productsDst = new ArrayList<>();
    private static List arrayList = new ArrayList();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel LesTextFields = new JPanel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    GridLayout gridLayout1 = new GridLayout(1, 2);
    GridLayout gridLayout2 = new GridLayout(10, 1);
    GridLayout gridLayout3 = new GridLayout(10, 1);
    GridLayout gridLayout4 = new GridLayout();
    JTextField name_Product = new JTextField();
    JComboBox jComboBox_ListOfLAbel = new JComboBox();
    JComboBox jComboBox_TypeOfProduct = new JComboBox(Type_product.values());
    JComboBox jComboBox_TypeOfDisplays = new JComboBox(Displays.values());
    JDateChooser jDateChooser_DDF = new JDateChooser();
    JDateChooser jDateChooser_DLC = new JDateChooser();
    JTextField jTextField_QuantityOfProduct = new JTextField();
    JTextField jTextField_PriceOfProduct = new JTextField();
    JButton jButton_CLOSE = new JButton();
    JButton jButton_CANCEL = new JButton();
    JButton jButton_SAVE = new JButton();

    JPanel LesLables = new JPanel();
    private BufferedReader br;
    private BufferedWriter bufWriter;
    private FileWriter fileWriter;
    private String name_product;
    private String nameOfLabel;
    private Date DDF;
    private Date DLC;
    private int quantity;
    private float price;
    private String type_product;
    private String type_displays;
    private String lineDst;
    //  String supermarket = Auth_MARKET.getLogin;
    private SuperMarket superMarket = new SuperMarket(Auth_MARKET.getLogin, Auth_MARKET.typeMarket);

    public AddProduct() throws IOException {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(638, 300));
        this.setTitle("       ENTER THE PRODUCTS       ");

        jPanel1.setLayout(gridLayout1);

        Label label = new Label();
        label.readFIle();

        for (int i = 0; i < Label.listOfLabel.size(); i++) {
            jComboBox_ListOfLAbel.addItem(Label.listOfLabel.get(i).toString());
        }
        jComboBox_ListOfLAbel.addItem(Label.listOfLabel);

        jLabel1.setText("     NAME OF PRODUCT");
        jLabel2.setText("     NAME OF LABEL");
        jLabel3.setText("     TYPE OF PRODUCT");
        jLabel4.setText("     TYPE ETALAGE");
        jLabel5.setText("     DATE BEGIN CONSUMPTION");
        jLabel6.setText("     DEADLINE CONSUMPTION");
        jLabel7.setText("     QUANTITY");
        jLabel8.setText("     PRICE");

        LesLables.setLayout(gridLayout2);

        LesTextFields.setLayout(gridLayout3);

        name_Product.setText("");
        jTextField_QuantityOfProduct.setText("");
        jTextField_PriceOfProduct.setText("");

        jButton_CLOSE.setText("PREVIOUS");
        jButton_CLOSE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Close(e);
            }
        });

        jButton_CANCEL.setText("CANCEL");
        jButton_CANCEL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cancel(e);
            }
        });

        jButton_SAVE.setText("SAVE");
        jButton_SAVE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    readDataFromFileDst();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                if (jDateChooser_DDF.getDate() == null
                        || jDateChooser_DLC.getDate() == null
                        || name_Product.getText() == null
                        || jTextField_QuantityOfProduct.getText() == null
                        || jTextField_PriceOfProduct.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs concernant ce produit SVP !".toUpperCase(),
                            "MESSAGE ERROR",
                            JOptionPane.WARNING_MESSAGE);
                    jDateChooser_DDF.requestFocus();
                    jDateChooser_DLC.requestFocus();
                    name_Product.requestFocus();
                    jTextField_QuantityOfProduct.requestFocus();
                    jTextField_PriceOfProduct.requestFocus();
                } else {

                    name_product = name_Product.getText().toUpperCase();
                    nameOfLabel = jComboBox_ListOfLAbel.getSelectedItem().toString();
                    type_product = jComboBox_TypeOfProduct.getSelectedItem().toString();
                    type_displays = jComboBox_TypeOfDisplays.getSelectedItem().toString();
                    DDF = jDateChooser_DDF.getDate();
                    DLC = jDateChooser_DLC.getDate();
                    quantity = Integer.parseInt(jTextField_QuantityOfProduct.getText().toUpperCase());
                    price = Float.parseFloat(jTextField_PriceOfProduct.getText().toUpperCase());
                    Product product = new Product(name_product, nameOfLabel, type_product, type_displays, DDF, DLC, quantity, price);
                    products.add(product);
//                  add product in data file text
                    saveDataInFile();
//                  RAZ the information in space of enter product after save product
                    name_Product.setText("");
                    jComboBox_TypeOfProduct.setSelectedIndex(0);
                    jComboBox_TypeOfDisplays.setSelectedIndex(0);
                    jTextField_QuantityOfProduct.setText("");
                    jTextField_PriceOfProduct.setText("");
                }
            }
        });
        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setLayout(gridLayout4);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButton_SAVE, null);
        jPanel2.add(jButton_CANCEL, null);
        jPanel2.add(jButton_CLOSE, null);

        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel1.add(LesLables, null);
        LesLables.add(jLabel1, null);
        LesLables.add(jLabel2, null);
        LesLables.add(jLabel3, null);
        LesLables.add(jLabel4, null);
        LesLables.add(jLabel5, null);
        LesLables.add(jLabel6, null);
        LesLables.add(jLabel7, null);
        LesLables.add(jLabel8, null);

        jPanel1.add(LesTextFields, null);
        LesTextFields.add(name_Product, null);
        LesTextFields.add(jComboBox_ListOfLAbel, null);
        LesTextFields.add(jComboBox_TypeOfProduct, null);
        LesTextFields.add(jComboBox_TypeOfDisplays, null);
        LesTextFields.add(jDateChooser_DDF, null);
        LesTextFields.add(jDateChooser_DLC, null);
        LesTextFields.add(jTextField_QuantityOfProduct, null);
        LesTextFields.add(jTextField_PriceOfProduct, null);
    }

    //jButton_CANCEL
    public void Cancel(ActionEvent e) {
        name_Product.setText("");
        jTextField_QuantityOfProduct.setText("");
        jTextField_PriceOfProduct.setText("");
        jComboBox_TypeOfProduct.setSelectedIndex(0);
        jComboBox_TypeOfDisplays.setSelectedIndex(0);
    }

    // jButton_CLOSE
    public void Close(ActionEvent e) {
        this.dispose();
    }

    @Override
    public void readDataFromFileSrc() {

    }

    @Override
    public void readDataFromFileDst() throws ParseException {
        productsDst.removeAll(productsDst);
        try {
            File file = new File("List Product of Market " + superMarket.getName().toString() + ".txt");
            if (file.exists() && file.length() != 0) {
                br = new BufferedReader(new FileReader("List Product of Market " + superMarket.getName().toString() + ".txt"));
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
    }

    @Override
    public void saveDataInFile() {
        arrayList.removeAll(arrayList);
        products.forEach(x -> {
            arrayList.add("Libele : " + name_Product.getText().toUpperCase() + " - " +
                    "Label : " + jComboBox_ListOfLAbel.getSelectedItem().toString() + " - " +
                    "Type produit : " + type_product + " - " +
                    "Type etalage : " + type_displays + " - " +
                    "DDF : " + format.format(jDateChooser_DDF.getDate()) + " - " +
                    "DLC : " + format.format(jDateChooser_DLC.getDate()) + " - " +
                    "Quantity : " + jTextField_QuantityOfProduct.getText().toUpperCase() + " - " +
                    "Price : " + jTextField_PriceOfProduct.getText().toUpperCase());
        });
        try {
            fileWriter = new FileWriter("List Product of Market " + superMarket.getName() + ".txt", true);
            bufWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < arrayList.size(); i++) {
                bufWriter.write(arrayList.get(i).toString());
            }
            bufWriter.newLine();
            bufWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}

