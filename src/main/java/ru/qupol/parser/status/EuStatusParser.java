package ru.qupol.parser.status;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.qupol.model.ServerStatus;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class EuStatusParser implements StatusParser {

    //    private static final String LINK = "https://worldofwarcraft.com/en-us/game/status/classic-eu";
    private final int TIMEOUT = 5000;

    public Map<String, ServerStatus> parse() {

        Document document = null;
        try {
            document = Jsoup.parse(new File("statuses.xml"), Charset.defaultCharset().name());
//            document = Jsoup.parse(new URL(LINK), TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Elements elements = document.getElementsByClass("Table-row");

        for (Element element : elements) {
            Elements cols = element.getElementsByTag("div");
            String serverName = cols.get(1).text();
            System.out.println(serverName);
        }

        return null;
    }

    public static Map<String, String> mapPopulation = null;

    public static Map<String, String> getMapNamePopulation() {
        if (mapPopulation != null) {
            return mapPopulation;
        }
        Map<String, String> map = new HashMap<>();
        map.put("Amnennar", "Full");
        map.put("Ashbringer", "Full");
        map.put("Auberdine", "Full");
        map.put("Bloodfang", "High");
        map.put("Chromie", "High");
        map.put("Dragon's Call", "Full");
        map.put("Dragonfang", "Medium");
        map.put("Dreadmist", "Full");
        map.put("Earthshaker", "Medium");
        map.put("Everlook", "Full");
        map.put("Finkle", "High");
        map.put("Firemaw", "Full");
        map.put("Flamegor", "Full");
        map.put("Flamelash", "Full");
        map.put("Gandling", "Full");
        map.put("Gehennas", "Full");
        map.put("Golemagg", "Full");
        map.put("Harbinger of Doom", "Medium");
        map.put("Heartstriker", "Medium");
        map.put("Hydraxian Waterlords", "Medium");
        map.put("Judgement", "Medium");
        map.put("Lakeshire", "Full");
        map.put("Lucifron", "Full");
        map.put("Mandokir", "High");
        map.put("Mirage Raceway", "Full");
        map.put("Mograine", "Full");
        map.put("Nethergarde Keep", "Full");
        map.put("Noggenfogger", "Full");
        map.put("Patchwerk", "Full");
        map.put("Pyrewood Village", "Full");
        map.put("Razorfen", "Full");
        map.put("Razorgore", "Full");
        map.put("Rhok'delar", "Full");
        map.put("Shazzrah", "Full");
        map.put("Skullflame", "High");
        map.put("Stonespine", "Full");
        map.put("Sulfuron", "Full");
        map.put("Ten Storms", "High");
        map.put("Transcendence", "Full");
        map.put("Venoxis", "Full");
        map.put("Wyrmthalak", "Full");
        map.put("Zandalar Tribe", "Full");

        map.put("Хроми", "-");
        map.put("Рок-Делар", "-");
        map.put("Пламегор", "-");
        map.put("Змейталак", "-");
        map.put("Вестник Рока", "-");
        mapPopulation = map;
        return mapPopulation;
    }
}
