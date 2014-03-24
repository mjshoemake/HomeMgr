package mjs.home.controllers;

import mjs.common.exceptions.ModelException;
import mjs.home.services.UsersService;
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

/**
 * REST service used to retrieve, update, and delete user data
 * from the database.
 */
@Controller
public class UserController {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Controller");

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody Object getUserList(Model model) {
        try {
            return usersService.getAllUsers();
        } catch (ModelException e) {
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            return new ServiceResponse(500, "Error", "An error occurred retrieving the list of users. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    @ResponseBody Object getUser(Model model, @PathVariable String userName) {
        try {
            return usersService.getUser(userName);
        } catch (ModelException e) {
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            return new ServiceResponse(500, "Error", "An error occurred retrieving user " + userName + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.DELETE)
    @ResponseBody public Object deleteUser(Model model, @PathVariable String userName) {
        try {
            User user = usersService.getUser(userName);
            usersService.deleteUser(user);
            return new ServiceResponse(200, "Success", "Successfully deleted user " + userName + ".");
        } catch (ModelException e) {
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            return new ServiceResponse(500, "Error", "An error occurred deleting user " + userName + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody public Object saveUser(Model model, @RequestBody User user) {
        try {
            return usersService.save(user);
        } catch (Exception e) {
            return new ServiceResponse(500, "Error", "An error occurred deleting user " + user.getUsername() + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody public Object updateUser(Model model, @RequestBody User user) {
        try {
            usersService.update(user);
            return new ServiceResponse(200, "Success", "Successfully updated user " + user.getUsername() + ".");
        } catch (Exception e) {
            return new ServiceResponse(500, "Error", "An error occurred updating user " + user.getUsername() + ". " + e.getMessage());
        }
    }

}
