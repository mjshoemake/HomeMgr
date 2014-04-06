package mjs.home.services;

import mjs.common.exceptions.ModelException;
import mjs.model.FoodCategory;
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
public class FoodCategoriesService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");

    public FoodCategoriesService() {
        super(FoodCategory.class);
    }

    public String saveFoodCategory(FoodCategory foodCategory) throws ModelException {
        try {
            if (foodCategory != null) {
                log.debug("Saving food category " + foodCategory.getName() + "...");
                String newId = save(foodCategory);
                log.debug("   Saved.  Returning pk: " + newId);
                return newId;
            } else {
                throw new Exception("Expected a valid food category but received null.");
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save the specified food category.", e);
            throw new ModelException("Unable to save the specified food category. " + e.getMessage());
        }
    }

    public FoodCategory getFoodCategoryByPK(int id) throws ModelException {
        try {
            List<FoodCategory> foodCategories = findByCriteria(Restrictions.eq("meal_categories_pk", new Integer(id)));
            if (foodCategories.size() == 1) {
                return foodCategories.get(0);
            } else if (foodCategories.size() > 1) {
                throw new Exception("More than one food category returned matching this ID but only one is expected.");
            } else if (foodCategories.size() == 0) {
                throw new Exception("No food categories found that match the specified ID.");
            } else {
                throw new Exception("Unexpected error. Number of food categories returned: " + foodCategories.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this food category (" + id + ").", e);
            throw new ModelException("Unable to retrieve this food category (" + id + "). " + e.getMessage());
        }
    }

    public List getAllFoodCategories() throws ModelException {
        try {
            return getAll("from FoodCategory");
        } catch (Exception e) {
            log.error("Unable to retrieve the food category list.", e);
            throw new ModelException("Unable to retrieve the food category list. " + e.getMessage());
        }
    }

    public void deleteFoodCategory(FoodCategory foodCategory) throws ModelException {
        try {
            if (foodCategory != null) {
                delete(foodCategory);
            } else {
                throw new Exception("Expected a valid food category but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this food category.", e);
            throw new ModelException("Unable to delete this food category. " + e.getMessage());
        }
    }
}
