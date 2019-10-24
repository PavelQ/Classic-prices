package ru.qupol.parser.status;

import ru.qupol.model.ServerStatus;

import java.util.Map;

public interface StatusParser {

    Map<String, ServerStatus> parse();
}
