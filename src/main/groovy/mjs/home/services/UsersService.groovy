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
@Service
@Transactional
class UsersService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Model");

    @Autowired
    SessionFactory sessionFactory

    def saveUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().save(user)
        } else {
            throw new ModelException("Unable to save this user. Expected a valid user but received null.")
        }
    }

    def getUsers() {
        try {
            def factory = sessionFactory
            def session = factory.getCurrentSession()
            def query = session.createQuery("from User")
            query.list()
        } catch (Exception e) {
            log.error("Failed to get user list.", e)
            e.printStackTrace()
        }
        //sessionFactory.getCurrentSession().createQuery("from User").list()
    }

    def deleteUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user)
        } else {
            throw new ModelException("Unable to delete this user. Expected a valid user but received null.")
        }
    }
}
