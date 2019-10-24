package ru.qupol;

public class Utils {

    private static final char RUB_CHAR = '\u20BD';

    public static Float getFloat(String priceText) {
        priceText = priceText.trim();
        if (priceText.contains(" "))
            return Float.parseFloat(priceText.substring(0, priceText.indexOf(" ")));
        if (priceText.contains(String.valueOf(RUB_CHAR))) {
            return Float.parseFloat(priceText.substring(0, priceText.indexOf(RUB_CHAR)));
        }
        if (priceText.isEmpty())
            return null;
        return Float.parseFloat(priceText);
    }
}
