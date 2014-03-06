package mjs.home.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import mjs.model.User;
import mjs.home.services.UsersService;

/**
 * REST service used to retrieve the current list of users.
 */

//@Autowired
@Controller
class UsersController {

    @Autowired
    UsersService usersService

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    @ResponseBody List getUserList() {
/*
        User user = new User();
        user.setSelected(new Boolean(false));
        user.setUser_pk(1);
        user.setUsername("mjshoemake");
        return [user];
*/

        usersService.users
    }
}
