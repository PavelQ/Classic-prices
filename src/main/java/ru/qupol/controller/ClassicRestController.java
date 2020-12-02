package ru.qupol.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.qupol.service.LoadDataService;

@RestController
public class ClassicRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(ClassicRestController.class);

    @Autowired
    public LoadDataService loadDataService;

    @GetMapping("/restclassic")
    public String restPrices() {
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            s = mapper.writeValueAsString(loadDataService.takeAllDataSorted(null));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOGGER.error("error on make json", e);
        }
        return s;
    }
}
