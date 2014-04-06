package mjs.home.services;

import mjs.common.exceptions.ModelException;
import mjs.model.Cookbook;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class CookbooksService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");

    public CookbooksService() {
        super(Cookbook.class);
    }

    public String saveCookbook(Cookbook cookbook) throws ModelException {
        try {
            if (cookbook != null) {
                log.debug("Saving cookbook " + cookbook.getName() + "...");
                String newId = save(cookbook);
                log.debug("   Saved.  Returning pk: " + newId);
                return newId;
            } else {
                throw new Exception("Expected a valid cookbook but received null.");
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save the specified cookbook.", e);
            throw new ModelException("Unable to save the specified cookbook. " + e.getMessage());
        }
    }

    public Cookbook getCookbookByPK(int id) throws ModelException {
        try {
            List<Cookbook> cookbooks = findByCriteria(Restrictions.eq("cookbooks_pk", new Integer(id)));
            if (cookbooks.size() == 1) {
                return cookbooks.get(0);
            } else if (cookbooks.size() > 1) {
                throw new Exception("More than one cookbook returned matching this ID but only one is expected.");
            } else if (cookbooks.size() == 0) {
                throw new Exception("No cookbooks found that match the specified ID.");
            } else {
                throw new Exception("Unexpected error. Number of cookbooks returned: " + cookbooks.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this cookbook (" + id + ").", e);
            throw new ModelException("Unable to retrieve this cookbook (" + id + "). " + e.getMessage());
        }
    }

    public List getAllCookbooks() throws ModelException {
        try {
            return getAll("from Cookbook");
        } catch (Exception e) {
            log.error("Unable to retrieve the cookbook list.", e);
            throw new ModelException("Unable to retrieve the cookbook list. " + e.getMessage());
        }
    }

    public void deleteCookbook(Cookbook cookbook) throws ModelException {
        try {
            if (cookbook != null) {
                delete(cookbook);
            } else {
                throw new Exception("Expected a valid cookbook but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this cookbook.", e);
            throw new ModelException("Unable to delete this cookbook. " + e.getMessage());
        }
    }
}
