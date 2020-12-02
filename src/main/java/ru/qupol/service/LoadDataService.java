package ru.qupol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.qupol.model.Price;
import ru.qupol.model.ServerStatus;
import ru.qupol.parser.*;
import ru.qupol.parser.actual.FunpayRuEuActualSourceParser;
import ru.qupol.parser.actual.PwlvlRuActualSourceParser;
import ru.qupol.parser.status.EuStatusParser;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class LoadDataService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoadDataService.class);
    private Map<String, Set<SourceCacheHolder>> classicParserHoldersSetMap;
    private Map<String, Set<SourceCacheHolder>> actualParserHoldersSetMap;

    //    classic
    @Autowired
    private PwlvlEuSourceParcer pwlvlEuSourceParser;

    @Autowired
    private PwlvlRuSourceParser pwlvlRuSourceParser;

    @Autowired
    private FunpayEuSourceParcer funpayEuSourceParcer;

    @Autowired
    private FunpayRuSourceParser funpayRuSourceParser;

    @Autowired
    private PlayerAuctionsSourceParser playerAuctionsSourceParser;

    //    actual
    @Autowired
    private FunpayRuEuActualSourceParser funpayRuEuActualSourceParser;

    @Autowired
    private PwlvlRuActualSourceParser pwlvlRuActualSourceParser;

    @PostConstruct
    public void postConstruct() {
        classicParserHoldersSetMap = initParserHoldersClassic();
        actualParserHoldersSetMap = initParserHoldersActual();
    }

    private void setPopulations(List<Price> prices) {
        Map<String, ServerStatus> serverStatusMap = (new EuStatusParser()).getServerStatusMap();
        for (Price price : prices) {
            ServerStatus serverStatus = serverStatusMap.get(price.getServer().getName());
            price.setServerStatus(serverStatus);
        }

    }

    public List<Price> takeAllDataSorted(String sources) {
        List<Price> priceList = loadClassicData(sources);
        Collections.sort(priceList);
        setPopulations(priceList);
        return priceList;
    }

    public List<Price> takeAllDataSortedActual(String sources) {
        List<Price> priceList = loadActualData(sources);
        Collections.sort(priceList);
//        setPopulations(priceList);
        return priceList;
    }

    private Map<String, Set<SourceCacheHolder>> initParserHoldersClassic() {
        LOGGER.info("init parsers classic");
        Map<String, Set<SourceCacheHolder>> map = new HashMap<>();
        map.put("pw", new HashSet<>() {{
            add(new SourceCacheHolder(pwlvlEuSourceParser));
            add(new SourceCacheHolder(pwlvlRuSourceParser));
        }});
        map.put("f", new HashSet<>() {{
            add(new SourceCacheHolder(funpayEuSourceParcer));
            add(new SourceCacheHolder(funpayRuSourceParser));
        }});
        map.put("pa", Collections.singleton(new SourceCacheHolder(playerAuctionsSourceParser)));
        int parsersCount = getParsersCount(map);
        LOGGER.info("parsers classic initiated: " + parsersCount);
        return map;
    }

    private Map<String, Set<SourceCacheHolder>> initParserHoldersActual() {
        LOGGER.info("init parsers actual");
        Map<String, Set<SourceCacheHolder>> map = new HashMap<>();
        map.put("pw", new HashSet<>() {{
            add(new SourceCacheHolder(pwlvlRuActualSourceParser));
        }});
        map.put("f", new HashSet<>() {{
            add(new SourceCacheHolder(funpayRuEuActualSourceParser));
        }});
        int parsersCount = getParsersCount(map);
        LOGGER.info("parsers actual initiated: " + parsersCount);
        return map;
    }

    private int getParsersCount(Map<String, Set<SourceCacheHolder>> map) {
        return map.values().stream().map(Set::size).reduce(0, Integer::sum);
    }

    private List<Price> loadData(Set<SourceCacheHolder> cachesToLoad) {
        List<Price> dataList = new ArrayList<>();
        LOGGER.info("starting parsers work");
        cachesToLoad.forEach(cacheToLoad -> dataList.addAll(cacheToLoad.loadData(false)));
        return dataList;
    }

    private List<Price> loadClassicData(String sources) {
        Set<SourceCacheHolder> cachesToLoad = cachesToLoadBySources(sources);
        return loadData(cachesToLoad);
    }

    private List<Price> loadActualData(String sources) {
        Set<SourceCacheHolder> cachesToLoad = cachesToLoadActualBySources(sources);
        return loadData(cachesToLoad);
    }

    public void updateData(String sources) {
        Set<SourceCacheHolder> sourcesToUpdate = cachesToLoadBySources(sources);
        LOGGER.info("starting parsers work");
        classicParserHoldersSetMap.values().forEach(
                sourceCacheHolders -> sourcesToUpdate.forEach(
                        sourceCacheHolder -> sourceCacheHolder.loadData(true)
                )
        );
    }

    private Set<SourceCacheHolder> cachesToLoadBySources(String sources, Map<String, Set<SourceCacheHolder>> parserHoldersSetMap) {
        LOGGER.info("preparing parsers");
        Set<String> parserCodes = parserHoldersSetMap.keySet();
        Set<SourceCacheHolder> cachesToLoad = new HashSet<>();
        if (sources == null || sources.isEmpty()) {
            LOGGER.info("loads all of parsers");
            for (Set<SourceCacheHolder> set : parserHoldersSetMap.values()) {
                cachesToLoad.addAll(set);
            }
        } else {
            for (String parserCode : parserCodes) {
                if (sources.contains(parserCode)) {
                    LOGGER.info("loads parser: " + parserCode);
                    cachesToLoad.addAll(parserHoldersSetMap.get(parserCode));
                }
            }
        }
        return cachesToLoad;
    }

    private Set<SourceCacheHolder> cachesToLoadBySources(String sources) {
        return cachesToLoadBySources(sources, classicParserHoldersSetMap);
    }

    private Set<SourceCacheHolder> cachesToLoadActualBySources(String sources) {
        return cachesToLoadBySources(sources, actualParserHoldersSetMap);
    }

}
