package ru.qupol.parser.actual;

import ru.qupol.parser.PwlvlEuSourceParcer;

public class PwlvlRuActualSourceParser extends PwlvlEuSourceParcer {
    private static final String SERVER_SOURCE = "http://www.pwlvl.ru/games/3/money/";

    public PwlvlRuActualSourceParser() {
        serverSource = SERVER_SOURCE;
    }
}
