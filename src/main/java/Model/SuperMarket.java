package Model;

import java.util.List;

/**
 * Created by slima_000 on 12/11/2016.
 */
public class SuperMarket {
    private Float price;
    private String name;
    private String city;
    private Type_Market type_market;
    private List<SuperMarket> destinations;

    public SuperMarket(String name, String city) {
        super();
        this.name = name;
        this.city = city;
    }

    public SuperMarket(String name, Float price, List<SuperMarket> destinations) {
        super();
        this.name = name;
        this.price = price;
        this.destinations = destinations;
    }

    public SuperMarket(String name, Type_Market type_market) {
        super();
        this.name = name;
        this.type_market = type_market;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Type_Market getType_market() {
        return type_market;
    }

    public List<SuperMarket> getDestinations() {
        return destinations;
    }

    public Float getPrice() {
        return price;
    }
}
