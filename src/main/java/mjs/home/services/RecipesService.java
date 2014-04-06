package mjs.home.services;

import mjs.common.exceptions.ModelException;
import mjs.model.Recipe;
import mjs.model.User;
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
public class RecipesService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");

    public RecipesService() {
        super(Recipe.class);
    }

    public String saveRecipe(Recipe recipe) throws ModelException {
        try {
            if (recipe != null) {
                log.debug("Saving recipe " + recipe.getName() + "...");
                String newId = save(recipe);
                log.debug("   Saved.  Returning pk: " + newId);
                return newId;
            } else {
                throw new Exception("Expected a valid recipe but received null.");
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save the specified recipe.", e);
            throw new ModelException("Unable to save the specified recipe. " + e.getMessage());
        }
    }

    public Recipe getRecipeByPK(int id) throws ModelException {
        try {
            List<Recipe> recipes = findByCriteria(Restrictions.eq("recipes_pk", new Integer(id)));
            if (recipes.size() == 1) {
                return recipes.get(0);
            } else if (recipes.size() > 1) {
                throw new Exception("More than one recipe returned matching this ID but only one is expected.");
            } else if (recipes.size() == 0) {
                throw new Exception("No recipes found that match the specified ID.");
            } else {
                throw new Exception("Unexpected error. Number of recipes returned: " + recipes.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this recipe (" + id + ").", e);
            throw new ModelException("Unable to retrieve this recipe (" + id + "). " + e.getMessage());
        }
    }

    public List getAllRecipes() throws ModelException {
        try {
            return getAll("from Recipe");
        } catch (Exception e) {
            log.error("Unable to retrieve the recipe list.", e);
            throw new ModelException("Unable to retrieve the recipe list. " + e.getMessage());
        }
    }

    public void deleteRecipe(Recipe recipe) throws ModelException {
        try {
            if (recipe != null) {
                delete(recipe);
            } else {
                throw new Exception("Expected a valid recipe but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this recipe.", e);
            throw new ModelException("Unable to delete this recipe. " + e.getMessage());
        }
    }
}
