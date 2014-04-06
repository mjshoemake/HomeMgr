package mjs.home.services;

import mjs.common.exceptions.ModelException;
import mjs.model.Meal;
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
public class MealsService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");

    public MealsService() {
        super(Meal.class);
    }

    public String saveMeal(Meal meal) throws ModelException {
        try {
            if (meal != null) {
                log.debug("Saving meal " + meal.getName() + "...");
                String newId = save(meal);
                log.debug("   Saved.  Returning pk: " + newId);
                return newId;
            } else {
                throw new Exception("Expected a valid meal but received null.");
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save the specified meal.", e);
            throw new ModelException("Unable to save the specified meal. " + e.getMessage());
        }
    }

    public Meal getMealByPK(int id) throws ModelException {
        try {
            List<Meal> meals = findByCriteria(Restrictions.eq("meals_pk", new Integer(id)));
            if (meals.size() == 1) {
                return meals.get(0);
            } else if (meals.size() > 1) {
                throw new Exception("More than one meal returned matching this ID but only one is expected.");
            } else if (meals.size() == 0) {
                throw new Exception("No meals found that match the specified ID.");
            } else {
                throw new Exception("Unexpected error. Number of meals returned: " + meals.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this meal (" + id + ").", e);
            throw new ModelException("Unable to retrieve this meal (" + id + "). " + e.getMessage());
        }
    }

    public List getAllMeals() throws ModelException {
        try {
            return getAll("from Meal");
        } catch (Exception e) {
            log.error("Unable to retrieve the meal list.", e);
            throw new ModelException("Unable to retrieve the meal list. " + e.getMessage());
        }
    }

    public void deleteMeal(Meal meal) throws ModelException {
        try {
            if (meal != null) {
                delete(meal);
            } else {
                throw new Exception("Expected a valid meal but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this meal.", e);
            throw new ModelException("Unable to delete this meal. " + e.getMessage());
        }
    }
}
