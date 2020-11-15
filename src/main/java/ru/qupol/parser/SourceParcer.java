package ru.qupol.parser;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.qupol.exception.parser.SourceLoadException;
import ru.qupol.model.Price;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.jsoup.nodes.Document;

public abstract class SourceParcer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SourceParcer.class);

    public abstract String getServerSource();

    public abstract int getTimeout();

    public List<Price> parse() {
        Document document;
        try {
            document = parseForDocument(getServerSource(), getTimeout());
        } catch (SourceLoadException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return null;
        }
        return handleDocument(document);
    }

    protected Document parseForDocument(String serverSource, int timeout) throws SourceLoadException {
        Document document = null;
        try {
            document = Jsoup.parse(new URL(serverSource), timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document == null) {
            throw new SourceLoadException("Source parsing failed , source=" + serverSource);
        }
        return document;
    }

    public abstract List<Price> handleDocument(Document document);
}
