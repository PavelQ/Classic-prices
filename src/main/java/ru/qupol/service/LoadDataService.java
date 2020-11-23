package ru.qupol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.qupol.model.Price;
import ru.qupol.model.ServerStatus;
import ru.qupol.parser.*;
import ru.qupol.parser.status.EuStatusParser;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class LoadDataService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoadDataService.class);
    private Map<String, Set<SourceCacheHolder>> parserHoldersSet;

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

    @PostConstruct
    public void postConstruct() {
        parserHoldersSet = initParserHolders();
    }

    private void setPopulations(List<Price> prices) {
        Map<String, ServerStatus> serverStatusMap = (new EuStatusParser()).getServerStatusMap();
        for (Price price : prices) {
            ServerStatus serverStatus = serverStatusMap.get(price.getServer().getName());
            price.setServerStatus(serverStatus);
        }

    }

    public List<Price> takeAllDataSorted(String sources) {
        List<Price> priceList = loadData(sources);
        Collections.sort(priceList);
        setPopulations(priceList);
        return priceList;
    }

    private Map<String, Set<SourceCacheHolder>> initParserHolders() {
        LOGGER.info("init parsers");
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
        int parsersCount = map.values().stream().map(Set::size).reduce(0, Integer::sum);
        LOGGER.info("parsers initiated: " + parsersCount);
        return map;
    }

    private List<Price> loadData(String sources) {
        Set<SourceCacheHolder> cachesToLoad = cachesToLoadBySources(sources);
        List<Price> dataList = new ArrayList<>();
        LOGGER.info("starting parsers work");
        cachesToLoad.forEach(cacheToLoad -> dataList.addAll(cacheToLoad.loadData(false)));
        return dataList;

    }

    public void updateData(String sources) {
        Set<SourceCacheHolder> sourcesToUpdate = cachesToLoadBySources(sources);
        LOGGER.info("starting parsers work");
        parserHoldersSet.values().forEach(
                sourceCacheHolders -> sourcesToUpdate.forEach(
                        sourceCacheHolder -> sourceCacheHolder.loadData(true)
                )
        );
    }

    private Set<SourceCacheHolder> cachesToLoadBySources(String sources) {
        LOGGER.info("preparing parsers");
        Set<String> parserCodes = parserHoldersSet.keySet();
        Set<SourceCacheHolder> cachesToLoad = new HashSet<>();
        if (sources == null || sources.isEmpty()) {
            LOGGER.info("loads all of parsers");
            for (Set<SourceCacheHolder> set : parserHoldersSet.values()) {
                cachesToLoad.addAll(set);
            }
        } else {
            for (String parserCode : parserCodes) {
                if (sources.contains(parserCode)) {
                    LOGGER.info("loads parser: " + parserCode);
                    cachesToLoad.addAll(parserHoldersSet.get(parserCode));
                }
            }
        }
        return cachesToLoad;
    }

}
