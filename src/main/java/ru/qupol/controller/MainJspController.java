package ru.qupol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.qupol.model.GameServer;
import ru.qupol.model.Price;
import ru.qupol.service.LoadDataService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainJspController {


    @Autowired
    public LoadDataService loadDataService;


    @RequestMapping(value = {"/prices", "/"})
    public String prices(@RequestParam(name = "sources", required = false) String sources, Model model) {
        List<Price> priceList = loadDataService.takeAllDataSorted(sources);
        String message = sources == null ? "" : "Data generated with next params:" + sources;
        List<String> sourcesList = priceList.stream().map(price -> price.getSource().toString()).distinct().collect(Collectors.toList());
        List<GameServer.Faction> factionsList = priceList.stream().map(price -> price.getServer().getFaction()).distinct().collect(Collectors.toList());
        model.addAttribute("message", message);
        model.addAttribute("prices", priceList);
        model.addAttribute("factions", factionsList);
        model.addAttribute("sourcesList", sourcesList);
        model.addAttribute("sources", sources);
        return "prices";
    }

    @PostMapping(value = {"/updateData"})
    public String updatePrices(@RequestParam(name = "sources", required = false) String sources, Model model) {
        model.addAttribute("sources", sources);
        loadDataService.updateData(sources);
        return "prices";
    }


}
