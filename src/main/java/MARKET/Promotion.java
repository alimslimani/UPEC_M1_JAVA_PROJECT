package MARKET;

import Model.Product;
import Model.SuperMarket;

import java.util.Date;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class Promotion extends Product {


    public Promotion(String name_product, String label, String type_product, String type_displays, Date ddf, Date dlc, int quantity, float price, Date date_trasfer, SuperMarket origin) {
        super(name_product, label, type_product, type_displays, ddf, dlc, quantity, price, date_trasfer, origin);
    }
}

