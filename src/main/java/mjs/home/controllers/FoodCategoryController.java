package mjs.home.controllers;

import mjs.common.exceptions.ModelException;
import mjs.home.services.FoodCategoriesService;
import mjs.model.FoodCategory;
import mjs.model.PrimaryKey;
import mjs.model.ServiceResponse;
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
public class FoodCategoryController {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Controller");

    @Autowired
    FoodCategoriesService foodCategoriesService;

    @RequestMapping(value = "/foodCategories", method = RequestMethod.GET)
    @ResponseBody Object getMealList(Model model) {
        try {
            log.debug("REST Call: getAllFoodCategories()");
            return foodCategoriesService.getAllFoodCategories();
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving the list of food categories. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/foodCategories/{foodCategoryPk}", method = RequestMethod.GET)
    @ResponseBody Object getMeal(Model model, @PathVariable int foodCategoryPk) {
        try {
            log.debug("REST Call: getFoodCategory(pk=" + foodCategoryPk + ")");
            return foodCategoriesService.getFoodCategoryByPK(foodCategoryPk);
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving food category " + foodCategoryPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/foodCategories/{foodCategoryPk}", method = RequestMethod.DELETE)
    @ResponseBody public Object deleteMeal(Model model, @PathVariable int foodCategoryPk) {
        try {
            log.debug("REST Call: deleteMeal(pk=" + foodCategoryPk + ")");
            FoodCategory foodCategory = foodCategoriesService.getFoodCategoryByPK(foodCategoryPk);
            log.debug("   Deleting food category " + foodCategory.getName() + "...");
            foodCategoriesService.deleteFoodCategory(foodCategory);
            log.debug("   Deleting food category " + foodCategory.getName() + "... Done.");
            return new ServiceResponse(200, "Success", "Successfully deleted food category " + foodCategory.getName() + ".");
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting food category " + foodCategoryPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/foodCategories", method = RequestMethod.POST)
    @ResponseBody public Object saveMeal(Model model, @RequestBody FoodCategory foodCategory) {
        try {
            return foodCategoriesService.save(foodCategory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting food category " + foodCategory.getName() + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/foodCategories", method = RequestMethod.PUT)
    @ResponseBody public Object updateMeal(Model model, @RequestBody FoodCategory foodCategory) {
        try {
            if (foodCategory.getMeal_categories_pk() == 0) {
                String newPk = foodCategoriesService.saveFoodCategory(foodCategory);
                return new PrimaryKey(newPk);
            } else {
                foodCategoriesService.update(foodCategory);
                return new ServiceResponse(200, "Success", "Successfully updated food category " + foodCategory.getName() + ".");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred updating food category " + foodCategory.getName() + ". " + e.getMessage());
        }
    }
}
