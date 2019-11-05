package ru.qupol.parser.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.qupol.model.GameServerType;
import ru.qupol.model.ServerStatus;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EuStatusParser implements StatusParser {

    //    private static final String LINK = "https://worldofwarcraft.com/en-us/game/status/classic-eu";
    private final int TIMEOUT = 5000;
    private static final Logger LOGGER = LoggerFactory.getLogger(EuStatusParser.class);

    private Map<String, ServerStatus> mapServerStatus = null;

    public Map<String, ServerStatus> getServerStatusMap() {
        if (mapServerStatus != null)
            return mapServerStatus;
        mapServerStatus = parse();
        return mapServerStatus;
    }

    public Map<String, ServerStatus> parse() {
        ClassPathResource cpr = new ClassPathResource("statuses.xml");
        Document document = null;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(cpr.getFile());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOGGER.error("Parse error " + e.getMessage(), e);
        }
        Element root = document.getDocumentElement();
        NodeList servers = root.getChildNodes();

        Map<String, ServerStatus> map = new HashMap<>();

        for (int i = 1; i < servers.getLength(); i += 2) {
            Node server = servers.item(i);
            NodeList values = server.getChildNodes();
            ServerStatus serverStatus = new ServerStatus();
            serverStatus.setServerName(values.item(1).getTextContent());
            serverStatus.setServerType(GameServerType.getType(values.item(3).getTextContent()));
            serverStatus.setPopulation(values.item(5).getTextContent());
            serverStatus.setServerLocation(values.item(9).getTextContent());
            map.put(serverStatus.getServerName(), serverStatus);
        }

        return map;
    }
}
