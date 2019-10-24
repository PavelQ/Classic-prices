package ru.qupol;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.qupol.model.Price;
import ru.qupol.parser.*;
import ru.qupol.parser.status.EuStatusParser;

import java.util.*;

@Controller
public class MainJspController {


    @RequestMapping(value = "/prices")
    public String prices(@RequestParam(name = "sources", required = false) String sources, Model model) {
        List<Price> priceList = takeAllDataSorted(sources);
        model.addAttribute("message", sources);
        model.addAttribute("prices", priceList);
        return "prices";

    }

    private void setPopulations(List<Price> prices) {
        for (Price price : prices) {
            String population = EuStatusParser.getMapNamePopulation().get(price.getServer().getName());
            price.setPopulation(population);
        }

    }

    private List<Price> takeAllDataSorted(String sources) {
        List<Price> priceList = new ArrayList<>();
        if (sources == null || sources.isEmpty()) {
            priceList.addAll(new PwlvlEuSourceParcer().parse());
            priceList.addAll((new PwlvlRuSourceParser()).parse());
            priceList.addAll((new FunpayEuSourceParcer()).parse());
            priceList.addAll((new FunpayRuSourceParser()).parse());
            priceList.addAll((new PlayerAuctionsSourceParser()).parse());
        } else {
            if (sources.contains("pw")) {
                priceList.addAll(new PwlvlEuSourceParcer().parse());
                priceList.addAll((new PwlvlRuSourceParser()).parse());
            }
            if (sources.contains("f")) {
                priceList.addAll((new FunpayEuSourceParcer()).parse());
                priceList.addAll((new FunpayRuSourceParser()).parse());
            }
            if (sources.contains("pa")) {
                priceList.addAll((new PlayerAuctionsSourceParser()).parse());
            }
        }
        Collections.sort(priceList);
        setPopulations(priceList);
        return priceList;
    }


    private Map<String, Set<SourceCacheHolder>> initParserHolders() {
        Map<String, Set<SourceCacheHolder>> map = new HashMap<>();
        map.put("pw", new HashSet<SourceCacheHolder>() {{
            add(new SourceCacheHolder(new PwlvlEuSourceParcer()));
            add(new SourceCacheHolder(new PwlvlRuSourceParser()));
        }});
        map.put("f", new HashSet<SourceCacheHolder>() {{
            add(new SourceCacheHolder(new FunpayEuSourceParcer()));
            add(new SourceCacheHolder(new FunpayRuSourceParser()));
        }});
        map.put("pa", Collections.singleton(new SourceCacheHolder(new PlayerAuctionsSourceParser())));
        return map;
    }


}
