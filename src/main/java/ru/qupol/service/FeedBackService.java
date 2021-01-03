package ru.qupol.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.qupol.dao.UniversalDaoService;
import ru.qupol.model.db.Feedback;
import ru.qupol.utils.SlfLogger;

import java.util.List;

@Service
public class FeedBackService {
    private static final Logger LOGGER = SlfLogger.getLogger();

    @Autowired
    private UniversalDaoService daoService;


    public void saveFeedback(String name, String email, String message) {
        LOGGER.info("saving message:" + message);
        Feedback feedback = new Feedback(name, email, message);
        daoService.saveObject(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return daoService.getAll(Feedback.class);
    }


}
