package ru.qupol.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.qupol.Utils;
import ru.qupol.model.Currency;
import ru.qupol.model.GameServer;
import ru.qupol.model.Price;
import ru.qupol.model.ServerSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PwlvlEuSourceParcer extends SourceParcer {


    private static final Logger LOGGER = LoggerFactory.getLogger(PwlvlEuSourceParcer.class);

    private final int TIMEOUT = 2000;
    protected String serverSource = "http://www.pwlvl.ru/games/142/money/";

    @Override
    public String getServerSource() {
        return serverSource;
    }

    @Override
    public int getTimeout() {
        return TIMEOUT;
    }

    @Override
    public List<Price> handleDocument(Document document) {
        Element element = document.body().getElementById("idTblOffers");
        Elements trs = element.getElementsByTag("tr");
        List<Price> priceList = new ArrayList<>(trs.size());
        for (Element tr : trs) {
            String serverName = tr.getElementsByTag("b").first().text();
            String priceText = tr.getAllElements().get(5).text();
            Float lowPrice = Utils.getFloat(priceText);
            Price price = new Price();
            price.setServer(new GameServer(serverName));
            price.setPrice(lowPrice);
            price.setCurrency(Currency.RUB);
            price.setCheckDate(new Date());
            price.setSource(ServerSource.PWLVL);

            priceList.add(price);
        }

        return priceList;
    }


}
