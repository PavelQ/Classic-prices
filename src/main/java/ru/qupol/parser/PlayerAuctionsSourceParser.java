package ru.qupol.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.qupol.model.Currency;
import ru.qupol.model.GameServer;
import ru.qupol.model.Price;
import ru.qupol.model.ServerSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PlayerAuctionsSourceParser implements SourceParcer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAuctionsSourceParser.class);

    private final int TIMEOUT = 100000;
    //    "https://www.playerauctions.com/wowc-gold/?sPid=8582&Serverid=8920&Quantity=50&PageIndex=1"
    private String serverBaseSource = "https://www.playerauctions.com/wowc-gold/";

    private float usdToRub = 63.0f;


    @Override
    public List<Price> parse() {
        List<Price> priceList = new ArrayList<>();
        for (String serverFullName : ServerId.serverNameCodeMap.keySet()) {
            Price price = null;
            try {
                price = parse1Server(serverFullName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (price != null)
                priceList.add(price);
        }
        LOGGER.info("parsing done");

        return priceList;
    }

    private Price parse1Server(String serverFullName) throws IOException {
        LOGGER.info("parsing: " + serverFullName);
        int serverId = ServerId.serverNameCodeMap.get(serverFullName);
        String serverSource = generateLink(serverId);

        Document document = null;
        try {
            document = Jsoup.parse(new URL(serverSource), TIMEOUT);
        } catch (MalformedURLException e) {
            LOGGER.error("error on make url " + serverSource + " server name: " + serverFullName, e);
        } catch (IOException e) {
            LOGGER.error("can't parse " + serverFullName, e);
            throw e;

        }

        Elements offerItems = document.getElementsByClass("offer-item");
        Element offerItem = offerItems.first();
        Elements offerElements = offerItem.getAllElements();
        String seller = offerElements.get(1).getElementsByTag("div").first().text();
        String offerPrice = offerItem.getElementsByClass("offer-price").first().text();

        float lowPrice = getPriceRubPerGold(getPricePerUsd(offerPrice));
        Price price = new Price();
        price.setSeller(seller);
        price.setPrice(lowPrice);
        price.setCurrency(Currency.RUB);
        price.setCheckDate(new Date());
        price.setSource(ServerSource.PLAYER_AUCTIONS);
        price.setServer(getGameServer(serverFullName));

        return price;
    }

    private float getPricePerUsd(String offerPrice) {
        LOGGER.debug("offerprice = " + offerPrice);
        int eqPos = offerPrice.indexOf('=');
        String goldPerUsdString = offerPrice.substring(eqPos + 1, offerPrice.length() - 5);
        return Float.parseFloat(goldPerUsdString);
    }

    private float getPriceRubPerGold(float pricePerUsd) {
        return usdToRub / pricePerUsd;
    }

    private GameServer.Faction getFaction(String serverFullName) {
        if (serverFullName.contains("Horde")) return GameServer.Faction.HORDE;
        if (serverFullName.contains("Alliance")) return GameServer.Faction.ALIANCE;
        return null;
    }

    private String getServerName(String serverFullName) {
        int delimeterIndex = serverFullName.indexOf('-');
        String serverName = serverFullName.substring(0, delimeterIndex);
        return serverName.trim();
    }

    private GameServer getGameServer(String serverFullName) {
        String serverName = getServerName(serverFullName);
        GameServer gameServer = new GameServer(serverName);
        GameServer.Faction faction = getFaction(serverFullName);
        gameServer.setFaction(faction);
        return gameServer;
    }

    private String getQuantityServerAttribute(int quantity) {
        return "Quantity=" + quantity;
    }

    private String getQuantityServerAttribute() {
        return getQuantityServerAttribute(100);
    }

    private String getPageIndexServerAttribute(int pageIndex) {
        return "PageIndex=" + pageIndex;
    }

    private String getPageIndexServerAttribute() {
        return getPageIndexServerAttribute(1);
    }

    private String generateLink(String localePid, String serverId, String quantity, String pageIndex) {
        return serverBaseSource + "?" + localePid
                + "&" + serverId
                + "&" + quantity
                + "&" + pageIndex
                + "&SortField=cheapest-price"
                ;
    }

    String generateLink(int serverId) {
        return generateLink(SeverLocalePid.getEuropePid(), ServerId.getServerId(serverId), getQuantityServerAttribute(), getPageIndexServerAttribute());
    }

    static class SeverLocalePid {
        private static final String EUROPE = "8583";
        private static final String USA = "8582";

        private static String getPid(String pid) {
            return "sPid=" + pid;
        }

        static String getUsaPid() {
            return getPid(USA);
        }

        static String getEuropePid() {
            return getPid(EUROPE);
        }
    }

    static class ServerId {
        static final Map<String, Integer> serverNameCodeMap = new HashMap<>();

        static {
            initMap();
        }

        static void initMap() {
            serverNameCodeMap.put("Amnennar - Alliance", 8894);
            serverNameCodeMap.put("Amnennar - Horde", 8895);
            serverNameCodeMap.put("Ashbringer - Alliance", 8892);
            serverNameCodeMap.put("Ashbringer - Horde", 8893);
            serverNameCodeMap.put("Auberdine - Alliance", 8807);
            serverNameCodeMap.put("Auberdine - Horde", 8808);
            serverNameCodeMap.put("Bloodfang - Alliance", 8896);
            serverNameCodeMap.put("Bloodfang - Horde", 8897);
            serverNameCodeMap.put("Chromie - Alliance", 8815);
            serverNameCodeMap.put("Chromie - Horde", 8816);
            serverNameCodeMap.put("Dragon's Call - Alliance", 8898);
            serverNameCodeMap.put("Dragon's Call - Horde", 8899);
            serverNameCodeMap.put("Dreadmist - Alliance", 8900);
            serverNameCodeMap.put("Dreadmist - Horde", 8901);
            serverNameCodeMap.put("Everlook - Alliance", 8811);
            serverNameCodeMap.put("Everlook - Horde", 8812);
            serverNameCodeMap.put("Finkle - Alliance", 8902);
            serverNameCodeMap.put("Finkle - Horde", 8903);
            serverNameCodeMap.put("Firemaw - Alliance", 8839);
            serverNameCodeMap.put("Firemaw - Horde", 8840);
            serverNameCodeMap.put("Flamegor - Alliance", 8817);
            serverNameCodeMap.put("Flamegor - Horde", 8818);
            serverNameCodeMap.put("Flamelash - Alliance", 8841);
            serverNameCodeMap.put("Flamelash - Horde", 8842);
            serverNameCodeMap.put("Gandling - Alliance", 8843);
            serverNameCodeMap.put("Gandling - Horde", 8844);
            serverNameCodeMap.put("Gehennas - Alliance", 8793);
            serverNameCodeMap.put("Gehennas - Horde", 8794);
            serverNameCodeMap.put("Golemagg - Alliance", 8795);
            serverNameCodeMap.put("Golemagg - Horde", 8796);
            serverNameCodeMap.put("Hydraxian Waterlords - Alliance", 8797);
            serverNameCodeMap.put("Hydraxian Waterlords - Horde", 8798);
            serverNameCodeMap.put("Judgement - Alliance", 8904);
            serverNameCodeMap.put("Judgement - Horde", 8905);
            serverNameCodeMap.put("Lakeshire - Alliance", 8906);
            serverNameCodeMap.put("Lakeshire - Horde", 8907);
            serverNameCodeMap.put("Lucifron - Alliance", 8813);
            serverNameCodeMap.put("Lucifron - Horde", 8814);
            serverNameCodeMap.put("Mirage Raceway - Alliance", 8799);
            serverNameCodeMap.put("Mirage Raceway - Horde", 8800);
            serverNameCodeMap.put("Mograine - Alliance", 8847);
            serverNameCodeMap.put("Mograine - Horde", 8848);
            serverNameCodeMap.put("Nethergarde Keep - Alliance", 8849);
            serverNameCodeMap.put("Nethergarde Keep - Horde", 8850);
            serverNameCodeMap.put("Noggenfogger - Alliance", 8908);
            serverNameCodeMap.put("Noggenfogger - Horde", 8909);
            serverNameCodeMap.put("Patchwerk - Alliance", 8910);
            serverNameCodeMap.put("Patchwerk - Horde", 8911);
            serverNameCodeMap.put("Pyrewood Village - Alliance", 8801);
            serverNameCodeMap.put("Pyrewood Village - Horde", 8802);
            serverNameCodeMap.put("Razorfen - Alliance", 8851);
            serverNameCodeMap.put("Razorfen - Horde", 8852);
            serverNameCodeMap.put("Razorgore - Alliance", 8853);
            serverNameCodeMap.put("Razorgore - Horde", 8854);
            serverNameCodeMap.put("Rhok'delar - Alliance", 8925);
            serverNameCodeMap.put("Rhok'delar - Horde", 8926);
            serverNameCodeMap.put("Shazzrah - Alliance", 8803);
            serverNameCodeMap.put("Shazzrah - Horde", 8804);
            serverNameCodeMap.put("Skullflame - Alliance", 8912);
            serverNameCodeMap.put("Skullflame - Horde", 8913);
            serverNameCodeMap.put("Stonespine - Alliance", 8857);
            serverNameCodeMap.put("Stonespine - Horde", 8858);
            serverNameCodeMap.put("Sulfuron - Alliance", 8809);
            serverNameCodeMap.put("Sulfuron - Horde", 8810);
            serverNameCodeMap.put("Ten Storms - Alliance", 8914);
            serverNameCodeMap.put("Ten Storms - Horde", 8915);
            serverNameCodeMap.put("Transcendence - Alliance", 8916);
            serverNameCodeMap.put("Transcendence - Horde", 8917);
            serverNameCodeMap.put("Venoxis - Alliance", 8855);
            serverNameCodeMap.put("Venoxis - Horde", 8856);
            serverNameCodeMap.put("Wyrmthalak - Alliance", 8923);
            serverNameCodeMap.put("Wyrmthalak - Horde", 8924);
            serverNameCodeMap.put("Zandalar Tribe - Alliance", 8805);
            serverNameCodeMap.put("Zandalar Tribe - Horde", 8806);
        }

        static String getServerId(int serverId) {
            return "Serverid=" + serverId;
        }

    }

}
