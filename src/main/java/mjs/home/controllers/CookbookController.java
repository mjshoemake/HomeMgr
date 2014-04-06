package mjs.home.controllers;

import mjs.common.exceptions.ModelException;
import mjs.home.services.CookbooksService;
import mjs.model.Cookbook;
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
public class CookbookController {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Controller");

    @Autowired
    CookbooksService cookbooksService;

    @RequestMapping(value = "/cookbooks", method = RequestMethod.GET)
    @ResponseBody Object getCookbookList(Model model) {
        try {
            log.debug("REST Call: getAllCookbooks()");
            return cookbooksService.getAllCookbooks();
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving the list of cookbooks. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/cookbooks/{cookbookPk}", method = RequestMethod.GET)
    @ResponseBody Object getCookbook(Model model, @PathVariable int cookbookPk) {
        try {
            log.debug("REST Call: getCookbook(pk=" + cookbookPk + ")");
            return cookbooksService.getCookbookByPK(cookbookPk);
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving cookbook " + cookbookPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/cookbooks/{cookbookPk}", method = RequestMethod.DELETE)
    @ResponseBody public Object deleteCookbook(Model model, @PathVariable int cookbookPk) {
        try {
            log.debug("REST Call: deleteCookbook(pk=" + cookbookPk + ")");
            Cookbook cookbook = cookbooksService.getCookbookByPK(cookbookPk);
            log.debug("   Deleting cookbook " + cookbook.getName() + "...");
            cookbooksService.deleteCookbook(cookbook);
            log.debug("   Deleting cookbook " + cookbook.getName() + "... Done.");
            return new ServiceResponse(200, "Success", "Successfully deleted cookbook " + cookbook.getName() + ".");
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting cookbook " + cookbookPk + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/cookbooks", method = RequestMethod.POST)
    @ResponseBody public Object saveCookbook(Model model, @RequestBody Cookbook cookbook) {
        try {
            return cookbooksService.save(cookbook);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting cookbook " + cookbook.getName() + ". " + e.getMessage());
        }
    }

    @RequestMapping(value = "/cookbooks", method = RequestMethod.PUT)
    @ResponseBody public Object updateCookbook(Model model, @RequestBody Cookbook cookbook) {
        try {
            if (cookbook.getCookbooks_pk() == 0) {
                String newPk = cookbooksService.saveCookbook(cookbook);
                return new PrimaryKey(newPk);
            } else {
                cookbooksService.update(cookbook);
                return new ServiceResponse(200, "Success", "Successfully updated cookbook " + cookbook.getName() + ".");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred updating cookbook " + cookbook.getName() + ". " + e.getMessage());
        }
    }
}
