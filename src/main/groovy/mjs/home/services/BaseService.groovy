package mjs.home.services

import org.hibernate.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;

/**
 * The base class for services which interact with the database through
 * Hibernate.
 */
@Service
class BaseService {

    @Autowired
    def sessionFactory

    public def getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    public List findByCriteria(def criterion, entityClass) {
        def criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }
}
