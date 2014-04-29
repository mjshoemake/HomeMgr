package mjs.home.controllers;

import mjs.common.core.BaseController;
import mjs.home.services.MealsService;
import mjs.model.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MealController extends BaseController {

    @Autowired
    MealsService service;

    public MealController() {
        super("mjs.model.Meal", "meal", "name", "meals_pk", "Meal");
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity getList(Model model) {
        return super.getList(model, service);
    }

    @RequestMapping(value = "/meals/{pk}", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity getByPK(Model model, @PathVariable int pk) {
        return super.getByPK(model, pk, service);
    }

    @RequestMapping(value = "/meals/{pk}", method = RequestMethod.DELETE)
    @ResponseBody public ResponseEntity delete(Model model, @PathVariable int pk) {
        return super.delete(model, pk, service);
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    @ResponseBody public ResponseEntity save(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

    @RequestMapping(value = "/meals", method = RequestMethod.PUT)
    @ResponseBody public ResponseEntity update(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

}
