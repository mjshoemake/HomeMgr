package mjs.home.controllers;

import mjs.common.exceptions.ModelException;
import mjs.home.services.MealsService;
import mjs.model.ServiceResponse;
import mjs.model.Meal;
import mjs.model.PrimaryKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * REST service used to retrieve, update, and delete user data
 * from the database.
 */
@Controller
public class MealController {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Controller");

    @Autowired
    MealsService mealsService;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    @ResponseBody Object getMealList(Model model) {
        try {
            log.debug("REST Call: getAllMeals()");
            return mealsService.getAllMeals();
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving the list of meals. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/meals/{mealPk}", method = RequestMethod.GET)
    @ResponseBody Object getMeal(Model model, @PathVariable int mealPk) {
        try {
            log.debug("REST Call: getMeal(pk=" + mealPk + ")");
            return mealsService.getMealByPK(mealPk);
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving meal " + mealPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/meals/{mealPk}", method = RequestMethod.DELETE)
    @ResponseBody public Object deleteMeal(Model model, @PathVariable int mealPk) {
        try {
            log.debug("REST Call: deleteMeal(pk=" + mealPk + ")");
            Meal meal = mealsService.getMealByPK(mealPk);
            log.debug("   Deleting meal " + meal.getName() + "...");
            mealsService.deleteMeal(meal);
            log.debug("   Deleting meal " + meal.getName() + "... Done.");
            return new ServiceResponse(200, "Success", "Successfully deleted meal " + meal.getName() + ".");
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting meal " + mealPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    @ResponseBody public Object saveMeal(Model model, @RequestBody Meal meal) {
        try {
            return mealsService.save(meal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting meal " + meal.getName() + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/meals", method = RequestMethod.PUT)
    @ResponseBody public Object updateMeal(Model model, @RequestBody Meal meal) {
        try {
            if (meal.getMeals_pk() == 0) {
                String newPk = mealsService.saveMeal(meal);
                return new PrimaryKey(newPk);
            } else {
                mealsService.update(meal);
                return new ServiceResponse(200, "Success", "Successfully updated meal " + meal.getName() + ".");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred updating meal " + meal.getName() + ". " + e.getMessage());
        }
    }

}
