package ru.qupol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.qupol.model.Price;
import ru.qupol.model.ServerStatus;
import ru.qupol.parser.*;
import ru.qupol.parser.status.EuStatusParser;

import java.util.*;

@Service
public class LoadDataService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoadDataService.class);

    private final Map<String, Set<SourceCacheHolder>> parserHoldersSet = initParserHolders();

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
            add(new SourceCacheHolder(new PwlvlEuSourceParcer()));
            add(new SourceCacheHolder(new PwlvlRuSourceParser()));
        }});
        map.put("f", new HashSet<>() {{
            add(new SourceCacheHolder(new FunpayEuSourceParcer()));
            add(new SourceCacheHolder(new FunpayRuSourceParser()));
        }});
        map.put("pa", Collections.singleton(new SourceCacheHolder(new PlayerAuctionsSourceParser())));
        LOGGER.info("parsers initiated: " + map.size());
        return map;
    }

    private List<Price> loadData(String sources) {
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
        List<Price> dataList = new ArrayList<>();
        LOGGER.info("starting parsers work");
        for (SourceCacheHolder cacheHolder : cachesToLoad) {
            dataList.addAll(cacheHolder.loadData(false));
        }
        return dataList;

    }

}
