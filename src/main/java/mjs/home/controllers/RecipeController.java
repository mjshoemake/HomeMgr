package mjs.home.controllers;

import mjs.common.exceptions.ModelException;
import mjs.home.services.RecipesService;
import mjs.model.PrimaryKey;
import mjs.model.ServiceResponse;
import mjs.model.Recipe;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * REST service used to retrieve, update, and delete recipe data
 * from the database.
 */
@Controller
public class RecipeController {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Controller");

    @Autowired
    RecipesService recipesService;

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    @ResponseBody Object getRecipeList(Model model) {
        try {
            log.debug("REST Call: getAllRecipes()");
            return recipesService.getAllRecipes();
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving the list of recipess. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/recipes/{recipePk}", method = RequestMethod.GET)
    @ResponseBody Object getRecipe(Model model, @PathVariable int recipePk) {
        try {
            log.debug("REST Call: getRecipe(pk=" + recipePk + ")");
            return recipesService.getRecipeByPK(recipePk);
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving recipe " + recipePk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/recipes/{recipePk}", method = RequestMethod.DELETE)
    @ResponseBody public Object deleteRecipe(Model model, @PathVariable int recipePk) {
        try {
            log.debug("REST Call: deleteRecipe(pk=" + recipePk + ")");
            Recipe recipe = recipesService.getRecipeByPK(recipePk);
            log.debug("   Deleting recipe " + recipe.getName() + "...");
            recipesService.deleteRecipe(recipe);
            log.debug("   Deleting recipe " + recipe.getName() + "... Done.");
            return new ServiceResponse(200, "Success", "Successfully deleted recipe " + recipe.getName() + ".");
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting recipe " + recipePk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    @ResponseBody public Object saveRecipe(Model model, @RequestBody Recipe recipe) {
        try {
            return recipesService.save(recipe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting recipe " + recipe.getName() + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.PUT)
    @ResponseBody public Object updateRecipe(Model model, @RequestBody Recipe recipe) {
        try {
            if (recipe.getMeals_pk() == 0) {
                String newPk = recipesService.saveRecipe(recipe);
                return new PrimaryKey(newPk);
            } else {
                recipesService.update(recipe);
                return new ServiceResponse(200, "Success", "Successfully updated meal " + recipe.getName() + ".");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred updating recipe " + recipe.getName() + ". " + e.getMessage());
        }
    }

}
