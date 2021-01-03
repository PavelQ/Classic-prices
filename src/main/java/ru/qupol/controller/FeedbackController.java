package ru.qupol.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.qupol.service.FeedBackService;
import ru.qupol.utils.SlfLogger;

@Controller
public class FeedbackController {
    private static final Logger LOGGER = SlfLogger.getLogger();

    @Autowired
    private FeedBackService feedBackService;

    @PostMapping(value = {"/sendFeedback"})
    public void sendFeedback(@RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "email", required = false) String email,
                             @RequestParam(name = "text", required = false) String message,
                             Model model) {
        LOGGER.info("feedback controller");
        feedBackService.saveFeedback(name, email, message);
    }

    @RequestMapping(value = {"/showFeedbacks"})
    public String getFeedBacks(Model model) {
        var feedbacks = feedBackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedbacks";
    }

}
