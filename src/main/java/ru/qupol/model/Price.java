package ru.qupol.model;

import java.util.Date;

public class Price implements Comparable<Price> {


    private float price;
    private Currency currency;
    private ServerSource source;
    private Date checkDate;
    private String seller;
    private GameServer server;
    private String population;

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ServerSource getSource() {
        return source;
    }

    public void setSource(ServerSource source) {
        this.source = source;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public GameServer getServer() {
        return server;
    }

    public void setServer(GameServer server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                ", currency=" + currency +
                ", source=" + source +
                ", checkDate=" + checkDate +
                ", seller='" + seller + '\'' +
                ", server=" + server +
                '}';
    }

    @Override
    public int compareTo(Price o) {
        int comp = this.server.compareTo(o.server);
        if (comp == 0) {
            if (this.price > o.price)
                return 1;
            else if (this.price < o.price)
                return -1;
            return 0;
        } else return comp;
    }
}
