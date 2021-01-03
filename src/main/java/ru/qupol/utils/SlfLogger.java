package ru.qupol.utils;

import org.slf4j.LoggerFactory;

public final class SlfLogger {
    public static org.slf4j.Logger getLogger() {
        var className = Thread.currentThread().getStackTrace()[2].getClassName();
        return LoggerFactory.getLogger(className);
    }
}


//vcaP&MQ1