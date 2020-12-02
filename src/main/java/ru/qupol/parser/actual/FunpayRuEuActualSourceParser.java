package ru.qupol.parser.actual;

import ru.qupol.parser.FunpayEuSourceParcer;

public class FunpayRuEuActualSourceParser extends FunpayEuSourceParcer {
    private static final String SERVER_SOURCE = "https://funpay.ru/chips/2/";

    public FunpayRuEuActualSourceParser() {
        serverSource = SERVER_SOURCE;
    }
}
