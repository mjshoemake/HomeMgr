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
            log.debug("REST Call: getAllUsers()");
            return usersService.getAllUsers();
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving the list of users. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users/{userPk}", method = RequestMethod.GET)
    @ResponseBody Object getUser(Model model, @PathVariable int userPk) {
        try {
            log.debug("REST Call: getUser(pk=" + userPk + ")");
            return usersService.getUserByPK(userPk);
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving user " + userPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users/{userPk}", method = RequestMethod.DELETE)
    @ResponseBody public Object deleteUser(Model model, @PathVariable int userPk) {
        try {
            log.debug("REST Call: deleteUser(pk=" + userPk + ")");
            User user = usersService.getUserByPK(userPk);
            log.debug("   Deleting user " + user.getUsername() + "...");
            usersService.deleteUser(user);
            log.debug("   Deleting user " + user.getUsername() + "... Done.");
            return new ServiceResponse(200, "Success", "Successfully deleted user " + user.getUsername() + ".");
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting user " + userPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody public Object saveUser(Model model, @RequestBody User user) {
        try {
            return usersService.save(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting user " + user.getUsername() + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody public Object updateUser(Model model, @RequestBody User user) {
        try {
            if (user.getUser_pk() == 0) {
                usersService.saveUser(user);
                return usersService.getUserByUsername(user.getUsername()).getUser_pk();
            } else {
                usersService.update(user);
                return new ServiceResponse(200, "Success", "Successfully updated user " + user.getUsername() + ".");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred updating user " + user.getUsername() + ". " + e.getMessage());
        }
    }

}
