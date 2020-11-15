package ru.qupol.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.qupol.model.Price;
import ru.qupol.model.ServerStatus;
import ru.qupol.parser.*;
import ru.qupol.parser.status.EuStatusParser;
import ru.qupol.service.LoadDataService;

import java.util.*;

@Controller
public class MainJspController {


    @Autowired
    public LoadDataService loadDataService;


    @RequestMapping(value = "/prices")
    public String prices(@RequestParam(name = "sources", required = false) String sources, Model model) {
        List<Price> priceList = loadDataService.takeAllDataSorted(sources);
        String message = sources == null ? "" : "Data generated with next params:" + sources;
        model.addAttribute("message", message);
        model.addAttribute("prices", priceList);
        return "prices";
    }


}
