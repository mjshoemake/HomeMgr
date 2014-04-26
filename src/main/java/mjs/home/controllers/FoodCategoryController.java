package mjs.home.controllers;

import mjs.common.core.BaseController;
import mjs.common.exceptions.ModelException;
import mjs.common.utils.LogUtils;
import mjs.home.services.FoodCategoriesService;
import mjs.home.services.MealsService;
import mjs.model.FoodCategory;
import mjs.model.Meal;
import mjs.model.PrimaryKey;
import mjs.model.ServiceResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FoodCategoryController extends BaseController {

    @Autowired
    FoodCategoriesService service;

    public FoodCategoryController() {
        super("mjs.model.FoodCategory", "food category", "name", "meal_categories_pk", "FoodCategory");
    }

    @RequestMapping(value = "/foodCategories", method = RequestMethod.GET)
    @ResponseBody public Object getList(Model model) {
        return super.getList(model, service);
    }

    @RequestMapping(value = "/foodCategories/{pk}", method = RequestMethod.GET)
    @ResponseBody public Object getByPK(Model model, @PathVariable int pk) {
        return super.getByPK(model, pk, service);
    }

    @RequestMapping(value = "/foodCategories/{pk}", method = RequestMethod.DELETE)
    @ResponseBody public Object delete(Model model, @PathVariable int pk) {
        return super.delete(model, pk, service);
    }

    @RequestMapping(value = "/foodCategories", method = RequestMethod.POST)
    @ResponseBody public Object save(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

    @RequestMapping(value = "/foodCategories", method = RequestMethod.PUT)
    @ResponseBody public Object update(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

}
