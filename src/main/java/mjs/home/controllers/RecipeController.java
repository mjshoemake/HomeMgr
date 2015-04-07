package mjs.home.controllers;

import mjs.common.core.BaseController;
import mjs.home.services.RecipesService;
import mjs.model.Recipe;
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

    @RequestMapping(value = "/recipes/{pk}", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity getByPK(Model model, @PathVariable int pk) {
        return super.getByPK(model, pk, service);
    }

    @RequestMapping(value = "/recipes/filter/{filter}", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity filterList(Model model, @PathVariable String filter) {
        return super.filterList(model, filter, service);
    }

    @RequestMapping(value = "/recipes/{filter}/sort/{sortFields}/dir/{sortDir}", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity filterList(Model model,
                                                   @PathVariable String filter,
                                                   @PathVariable String sortFields,
                                                   @PathVariable String sortDir) {
        return super.filterList(model, filter, sortFields, sortDir, service);
    }

    @RequestMapping(value = "/recipes/{pkList}", method = RequestMethod.DELETE)
    @ResponseBody public ResponseEntity delete(Model model, @PathVariable String pkList) {
        return super.delete(model, pkList, service);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    @ResponseBody public ResponseEntity save(Model model, @RequestBody Recipe entity) {
        return super.update(model, entity, service);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.PUT)
    @ResponseBody public ResponseEntity update(Model model, @RequestBody Recipe entity) {
        return super.update(model, entity, service);
    }

}
