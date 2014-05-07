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
import org.springframework.http.ResponseEntity;
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
    @ResponseBody public ResponseEntity getList(Model model) {
        return super.getList(model, service);
    }

    @RequestMapping(value = "/recipes/{filter}", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity filterList(Model model, @PathVariable String filter) {
        return super.filterList(model, filter, service);
    }

    @RequestMapping(value = "/recipes/{pkList}", method = RequestMethod.DELETE)
    @ResponseBody public ResponseEntity delete(Model model, @PathVariable String pkList) {
        return super.delete(model, pkList, service);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    @ResponseBody public ResponseEntity save(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.PUT)
    @ResponseBody public ResponseEntity update(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

}
