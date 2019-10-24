package ru.qupol.parser;

public class PwlvlRuSourceParser extends PwlvlEuSourceParcer {
    private static final String SERVER_SOURCE = "http://www.pwlvl.ru/games/141/money/";

    public PwlvlRuSourceParser() {
        serverSource = SERVER_SOURCE;
    }
}
