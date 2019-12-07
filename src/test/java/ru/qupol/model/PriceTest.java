package ru.qupol.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Test
    void compareTo_PriceLowerThenAnotherByName_True() {
        Price p1 = new Price();
        p1.setServer(new GameServer("1"));
        Price p2 = new Price();
        p2.setServer(new GameServer("2"));
        Assertions.assertTrue(p1.compareTo(p2) < 0);
    }

    @Test
    void compareTo_PricesEqualsByName_True(){
        Price p1 = new Price();
        p1.setServer(new GameServer("1"));
        Price p2 = new Price();
        p2.setServer(new GameServer("1"));
        Assertions.assertEquals(0, p1.compareTo(p2));
    }

    @Test
    void compareTo_PriceLowerThenAnotherByPrice_True(){
        Price p1 = new Price();
        p1.setServer(new GameServer("1"));
        p1.setPrice(1.0f);
        Price p2 = new Price();
        p2.setServer(new GameServer("1"));
        p2.setPrice(2.0f);
        Assertions.assertTrue(p1.compareTo(p2) < 0);
    }

    @Test
    void compareTo_PricesEqualsByPrice_True(){
        Price p1 = new Price();
        p1.setServer(new GameServer("1"));
        p1.setPrice(1.0f);
        Price p2 = new Price();
        p2.setServer(new GameServer("1"));
        p2.setPrice(1.0f);
        Assertions.assertEquals(0, p1.compareTo(p2));
    }
}