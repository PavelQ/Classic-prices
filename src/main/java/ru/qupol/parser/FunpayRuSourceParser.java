package ru.qupol.parser;

public class FunpayRuSourceParser extends FunpayEuSourceParcer {
    private static final String SERVER_SOURCE = "https://funpay.ru/chips/114/";

    public FunpayRuSourceParser() {
        serverSource = SERVER_SOURCE;
    }
}
