package ru.qupol.parser;

import org.jsoup.Jsoup;
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

import java.net.URL;
import java.util.*;

public class FunpayEuSourceParcer implements SourceParcer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunpayEuSourceParcer.class);

    private final int TIMEOUT = 5000;
    protected String serverSource = "https://funpay.ru/chips/118/";

    @Override
    public List<Price> parse() {
        Document document;
        try {
            document = Jsoup.parse(new URL(serverSource), TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Elements rows = document.getElementsByClass("tc-item");
        List<Price> priceList = new ArrayList<>();
        for (Element row : rows) {
            if (row.hasAttr("hidden-xxs")) continue;
            Price price = new Price();

            String serverName = row.getElementsByClass("tc-server").text();
            if (serverName.contains("’"))
                serverName = serverName.replace('’', '\'');
            if (serverName.equals("Любой")) continue;

            String faction = row.getElementsByClass("tc-side").text();
            GameServer gameServer = new GameServer(serverName);
            if (faction.equals("Орда")) {
                gameServer.setFaction(GameServer.Faction.HORDE);
            } else {
                gameServer.setFaction(GameServer.Faction.ALIANCE);
            }
            price.setServer(gameServer);

            price.setSource(ServerSource.FUNPAY);
            price.setCheckDate(new Date());
            price.setCurrency(Currency.RUB);
            String priceCount = row.getElementsByClass("tc-price price").text();
            if (priceCount.trim().isEmpty()) {
                LOGGER.error("wrong price: " + priceCount + " " + gameServer);
                continue;
            }
            price.setPrice(Utils.getFloat(priceCount));

            String seller = row.getElementsByClass("tc-user").text();
            price.setSeller(seller);

            priceList.add(price);


        }

        return findMinimalPrices(priceList);
    }


    private List<Price> findMinimalPrices(List<Price> fullPriceList) {
        Map<GameServer, Price> minimalMap = new HashMap<>();
        for (Price price : fullPriceList) {
            if (!minimalMap.containsKey(price.getServer())) {
                minimalMap.put(price.getServer(), price);
                continue;
            }
            Price actual = minimalMap.get(price.getServer());

            if (actual.getPrice() > price.getPrice()) {
                minimalMap.put(price.getServer(), price);
            }
        }
        return new ArrayList<>(minimalMap.values());
    }
}
