package ru.qupol.parser;

import ru.qupol.model.Price;

import java.util.List;

public interface SourceParcer {
    List<Price> parse();
}
