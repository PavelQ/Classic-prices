package ru.qupol.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qupol.utils.SlfLogger;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


@Service
public class UniversalDaoService {

    private static final Logger LOGGER = SlfLogger.getLogger();

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public <T> void saveObject(T object) {
        LOGGER.info("saving: " + object);
        Session session = sessionFactory.getCurrentSession();
//        session.persist(object);
        session.save(object);
    }

    @Transactional
    public <T> void saveObjects(List<T> objects) {
        LOGGER.info("saving servers count = " + objects.size());
        Session session = sessionFactory.getCurrentSession();
        objects.forEach(session::save);
        session.flush();
    }

    @Transactional
    public <T> List<T> getAll(Class<T> type) {
        LOGGER.info("getting all of " + type.getName());
        Session session = sessionFactory.getCurrentSession();
        CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(type);
        query.from(type);
        return session.createQuery(query).getResultList();
    }


}
