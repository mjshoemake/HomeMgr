package mjs.home.controllers;

import mjs.common.core.BaseController;
import mjs.common.exceptions.ModelException;
import mjs.home.services.MealsService;
import mjs.home.services.UsersService;
import mjs.model.Meal;
import mjs.model.PrimaryKey;
import mjs.model.ServiceResponse;
import mjs.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController {

    @Autowired
    UsersService service;

    public UserController() {
        super("mjs.model.User", "users", "fname+lname", "user_pk", "User");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody public Object getList(Model model) {
        return super.getList(model, service);
    }

    @RequestMapping(value = "/users/{pk}", method = RequestMethod.GET)
    @ResponseBody public Object getByPK(Model model, @PathVariable int pk) {
        return super.getByPK(model, pk, service);
    }

    @RequestMapping(value = "/users/{pk}", method = RequestMethod.DELETE)
    @ResponseBody public Object delete(Model model, @PathVariable int pk) {
        return super.delete(model, pk, service);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody public Object save(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody public Object update(Model model, @RequestBody Meal entity) {
        return super.update(model, entity, service);
    }

}
