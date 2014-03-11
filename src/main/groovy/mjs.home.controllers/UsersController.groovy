package mjs.home.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.ui.Model
import mjs.model.User
import mjs.home.services.UsersService

/**
 * REST service used to retrieve, update, and delete user data
 * from the database.
 */
//@Autowired
@Controller
class UsersController {

    @Autowired
    def usersService

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    @ResponseBody List getUserList() {
        usersService.users
    }

    @RequestMapping(value = "/deleteUser/(userName)", method = RequestMethod.GET)
    def deleteUser(Model model, @PathVariable String userName) {
        usersService.deleteUser()
    }


}
