package ru.qupol.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.qupol.Utils;
import ru.qupol.model.Currency;
import ru.qupol.model.GameServer;
import ru.qupol.model.Price;
import ru.qupol.model.ServerSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PwlvlEuSourceParcer implements SourceParcer {

    private final int TIMEOUT = 2000;
    protected String serverSource = "http://www.pwlvl.ru/games/142/money/";

    @Override
    public List<Price> parse() {

        Document document = null;
        try {
            document = Jsoup.parse(new URL(serverSource), TIMEOUT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
