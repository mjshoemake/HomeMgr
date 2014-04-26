package mjs.home.controllers;

import mjs.common.core.BaseController;
import mjs.common.exceptions.ModelException;
import mjs.home.services.MealsService;
import mjs.home.services.RecipesService;
import mjs.model.Meal;
import mjs.model.PrimaryKey;
import mjs.model.ServiceResponse;
import mjs.model.Recipe;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController extends BaseController {

    @Autowired
    RecipesService service;

    public RecipeController() {
        super("mjs.model.Recipe", "recipe", "name", "recipes_pk", "Recipe");
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    @ResponseBody public Object getList(Model model) {
        return super.getList(model, service);
    }

    @RequestMapping(value = "/recipes/{pk}", method = RequestMethod.GET)
    @ResponseBody public Object getByPK(Model model, @PathVariable int pk) {
        return super.getByPK(model, pk, service);
    }

    @RequestMapping(value = "/recipes/{pk}", method = RequestMethod.DELETE)
    @ResponseBody public Object delete(Model model, @PathVariable int pk) {
        return super.delete(model, pk, service);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    @ResponseBody public Object save(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.PUT)
    @ResponseBody public Object update(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

}
