package mjs.home.services;

import java.util.List;
import mjs.common.exceptions.ModelException;
import mjs.common.utils.LogUtils;
import mjs.model.User;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class UsersService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");

    public UsersService() {
        super(User.class                                                                                                                                                                                                                                                                                                         );
    }

    public String saveUser(User user) throws ModelException {
        try {
            if (user != null) {
                log.debug("Saving user " + user.getUsername() + "...");
                String newId = save(user);
                log.debug("   Saved.  Returning pk: " + newId);
                return newId;
            } else {
                throw new Exception("Expected a valid user but received null.");
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save the specified user.", e);
            throw new ModelException("Unable to save the specified user. " + e.getMessage());
        }
    }

    public User getUser(String userName) throws ModelException {
        try {
            List<User> users = findByCriteria(Restrictions.like("username", userName, MatchMode.START));
            if (users.size() == 1) {
                return users.get(0);
            } else if (users.size() > 1) {
                throw new Exception("More than one user returned matching this username but only one is expected.");
            } else if (users.size() == 0) {
                throw new Exception("No users found that match the specified username.");
            } else {
                throw new Exception("Unexpected error. Number of users returned: " + users.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this user (" + userName + ").", e);
            throw new ModelException("Unable to retrieve this user (" + userName + "). " + e.getMessage());
        }
    }

    public User checkUser(String userName) throws ModelException {
        try {
            List<User> users = findByCriteria(Restrictions.like("username", userName, MatchMode.START));
            if (users.size() == 1) {
                return users.get(0);
            } else if (users.size() > 1) {
                throw new Exception("More than one user returned matching this username but only one is expected.");
            } else if (users.size() == 0) {
                return null;
            } else {
                throw new Exception("Unexpected error. Number of users returned: " + users.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this user (" + userName + ").", e);
            throw new ModelException("Unable to retrieve this user (" + userName + "). " + e.getMessage());
        }
    }

    public List getAllUsers() throws ModelException {
        try {
            return getAll("from User");
        } catch (Exception e) {
            log.error("Unable to retrieve the user list.", e);
            throw new ModelException("Unable to retrieve the user list. " + e.getMessage());
        }
    }

    public void deleteUser(User user) throws ModelException {
        try {
            if (user != null) {
                delete(user);
            } else {
                throw new Exception("Expected a valid user but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this user.", e);
            throw new ModelException("Unable to delete this user. " + e.getMessage());
        }
    }
}
