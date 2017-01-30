package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class Product extends Observable implements Serializable {
    private static final long serialVersionUID = 1350092881346723535L;
    private String name_product;
    private String label;
    private String type_product;
    private String type_displays;
    private Date ddf;
    private Date dlc;
    private Date date_SOLD;
    private Date promotionDate;
    private int quantity;
    private float price;
    private SuperMarket superMarkets;
    private SuperMarket origin;

//    public Product() {
//        System.out.println("Empty args ctor called");
////        setName_product("");
////        setQuantity(0);
//    }

//    public Product(String name, int quantity) {
//        setName_product(name);
//        setQuantity(quantity);
//    }

    //    constructeur pour l'AJOUT de chaque PRODUIT
//    avec toutes les informations nécessaire à ce dernier
    public Product(String name_product, String label, String type_product, String type_displays, Date ddf, Date dlc, int quantity, float price) {
        super();
        this.name_product = name_product;
        this.label = label;
        this.type_product = type_product;
        this.type_displays = type_displays;
        this.ddf = ddf;
        this.dlc = dlc;
        this.quantity = quantity;
        this.price = price;
        this.notifyObservers();
    }

    //    constructeur pour l'AJOUT de promotion
//    avec toutes les informations nécessaire
    public Product(String name_product, String label, Date ddf, Date dlc, int quantity, SuperMarket superMarket) {
        super();
        this.name_product = name_product;
        this.label = label;
        this.ddf = ddf;
        this.dlc = dlc;
        this.quantity = quantity;
        this.superMarkets = superMarket;
        this.notifyObservers();
    }

    //    constructeur pour le produit vendu
//    avec toutes les informations nécessaire
    public Product(String name_product, String label, String type_product, String type_displays, Date ddf, Date dlc, int quantity, float price, Date date_sold) {
        super();
        this.name_product = name_product;
        this.label = label;
        this.type_product = type_product;
        this.type_displays = type_displays;
        this.ddf = ddf;
        this.dlc = dlc;
        this.quantity = quantity;
        this.price = price;
        this.date_SOLD = date_sold;
        this.notifyObservers();
    }

    //    constructeur pour le produit transferer avec le market origine
//    avec toutes les informations nécessaire
    public Product(String name_product, String label, String type_product, String type_displays, Date ddf, Date dlc, int quantity, float price, Date date_trasfer, SuperMarket origin) {
        super();
        this.name_product = name_product;
        this.label = label;
        this.type_product = type_product;
        this.type_displays = type_displays;
        this.ddf = ddf;
        this.dlc = dlc;
        this.quantity = quantity;
        this.price = price;
        this.promotionDate = date_trasfer;
        this.notifyObservers();
    }

    public Product(String name_product, String label, String type_product, String type_displays, Date ddf, Date dlc, int quantity, float price, SuperMarket origin) {
        super();
        this.name_product = name_product;
        this.label = label;
        this.type_product = type_product;
        this.type_displays = type_displays;
        this.ddf = ddf;
        this.dlc = dlc;
        this.quantity = quantity;
        this.price = price;
        this.notifyObservers();
    }

    //  use generique methode
    public static <T, S> Set<S> mapToSet(List<T> ls, Function<T, S> f) {
        return ls.stream()
                .map(f)
                .sorted()
                .collect(toSet());
    }

    public static <T, S> Set<S> flatMapToSet(List<T> ls, Function<T, Stream<S>> f) {
        return ls.stream()
                .flatMap(f)
                .sorted()
                .collect(toSet());
    }

    public static Set<String> priceSupThirty(List<Product> productStream) {
        return productStream.stream()
                .filter(a -> a.getPrice() > 30.0)
                .map(Product::getName_product)
                .collect(Collectors.toSet());
    }

    //  method to get name of product in List of product
    public static Set<String> name_product(List<Product> products) {
        return mapToSet(products, Product::getName_product);
    }

    //  method to get type of displays with a product selected in List
    public static Set<String> type_Displays(List<Product> products) {
//        without generic method
//        return products.stream()
//                .map(x -> x.getType_displays())
//                .sorted()
//                .collect(Collectors.toSet());
//        with a generic method
        return mapToSet(products, Product::getType_displays);
    }

    public Date getPromotionDate() {
        return promotionDate;
    }

    //  method to get price thirty with a specific product in List

//    ajout d'un parametre pour un filtre par selection de l'étalage

    public void setPromotionDate(Date promotionDate) {
        this.promotionDate = promotionDate;
        notifyObservers();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        notifyObservers();
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.notifyObservers();
        this.name_product = name_product;
    }

    public String getType_product() {
        return type_product;
    }

    public void setType_product(String type_product) {
        this.type_product = type_product;
        notifyObservers();
    }

    public String getType_displays() {
        return type_displays;
    }

    public void setType_displays(String type_displays) {
        this.type_displays = type_displays;
        notifyObservers();
    }

    public Date getDDF() {
        return ddf;
    }

    public void setDDF(Date ddf) {
        this.ddf = ddf;
        notifyObservers();
    }

    public Date getDLC() {
        return dlc;
    }

    public void setDLC(Date dlc) {
        this.dlc = dlc;
        this.notifyObservers();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.notifyObservers();
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public float setPrice(float price) {
        this.price = price;
        this.notifyObservers();
        return price;
    }

    public SuperMarket getSuperMarkets() {
        return superMarkets;
    }

    public void setSuperMarkets(SuperMarket superMarkets) {
        this.superMarkets = superMarkets;
        this.notifyObservers();
    }

    public Date getDate_SOLD() {
        return date_SOLD;
    }

    public void setDate_SOLD(Date date_SOLD) {
        this.date_SOLD = date_SOLD;
        this.notifyObservers();
    }

    public SuperMarket getOrigin() {
        return origin;
    }

    public void setOrigin(SuperMarket origin) {
        this.origin = origin;
        this.notifyObservers();
    }

    public Stream<String> getDestinationCities() {
        return origin.getDestinations()
                .stream()
                .map(SuperMarket::getCity);
    }

//    public String toString() {
//        return getName_product() + "(" + getQuantity() + ")";
//    }

}
