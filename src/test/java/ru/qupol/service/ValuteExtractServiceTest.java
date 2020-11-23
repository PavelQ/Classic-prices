package ru.qupol.service;

import org.junit.jupiter.api.Test;
import ru.qupol.exception.parser.ValuteLoadException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValuteExtractServiceTest {

    ValuteExtractService valuteExtractService = new ValuteExtractService();

    @Test
    public void loadAllURL_successful() throws ValuteLoadException {
        var valutes = valuteExtractService.extractXMLCurrencies();
        assertFalse(valutes.isEmpty());
    }

    @Test
    public void loadedUSD_successful() throws ValuteLoadException {
        assertNotNull(valuteExtractService.getUSDValute());
    }

    @Test
    public void loadedEUR_successful() throws ValuteLoadException {
        assertNotNull(valuteExtractService.getEURValute());
    }

}