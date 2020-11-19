package ru.qupol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.qupol.exception.parser.ValuteLoadException;
import ru.qupol.model.valute.ValCurs;
import ru.qupol.model.valute.Valute;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class ValuteExtractService {
    private static final String cbrLink = "http://www.cbr.ru/scripts/XML_daily.asp";

    private static final Logger LOGGER = LoggerFactory.getLogger(ValuteExtractService.class);


    private Date loadDate = null;
    private List<Valute> valutes = null;

    public List<Valute> ExtractXMLCurrencies() throws ValuteLoadException {

        long lifetime = 1000 * 60 * 60 * 24; // 1 day
        if (loadDate == null || (new Date()).after(new Date(loadDate.getTime() + lifetime))) {
            loadDate = new Date();
            valutes = loadXMLCurrencies();
        }
        return valutes;


    }

    /**
     * 3 chars code, example USD, EUR
     *
     * @param charCode 3 chars valute code
     * @return valute
     */
    public Valute getValuteByCode(String charCode) throws ValuteLoadException {
        List<Valute> valutes = ExtractXMLCurrencies();
        return valutes.stream().filter(valute -> valute.getCharCode().equals(charCode)).findFirst().orElseThrow();
    }

    public Valute getUSDValute() throws ValuteLoadException {
        return getValuteByCode("USD");
    }

    public Valute getEURValute() throws ValuteLoadException {
        return getValuteByCode("EUR");
    }


    private List<Valute> loadXMLCurrencies() throws ValuteLoadException {
        URL url;
        try {
            url = new URL(cbrLink);
        } catch (MalformedURLException e) {
            LOGGER.error("wrong url for load valutes : " + cbrLink, e);
            throw new ValuteLoadException();

        }
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);
        } catch (JAXBException e) {
            LOGGER.error("can't create JAXB instance " + ValCurs.class.getName(), e);
            throw new ValuteLoadException();
        }
        Unmarshaller jaxbUnmarshaller;
        try {
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ValuteLoadException();
        }
        try {
            ValCurs valCurs = (ValCurs) jaxbUnmarshaller.unmarshal(url);
            return valCurs.getValutes();
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ValuteLoadException();
        }
    }


}
