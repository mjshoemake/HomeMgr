package mjs.home.services

import org.apache.log4j.Logger
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
import mjs.common.exceptions.ModelException
import mjs.model.User
import org.springframework.transaction.annotation.Transactional

/**
 * Spring service used to retrieve the current list of users.
 */
@Transactional
class UsersService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Model");

    def saveUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().save(user)
        } else {
            throw new ModelException("Unable to save this user. Expected a valid user but received null.")
        }
    }

    def getUser(def userName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
        criteria.add(criterion);
        return criteria.list();
    }

    def getUsers() {
        sessionFactory.getCurrentSession().createQuery("from User").list()
    }

    def deleteUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user)
        } else {
            throw new ModelException("Unable to delete this user. Expected a valid user but received null.")
        }
    }
}
