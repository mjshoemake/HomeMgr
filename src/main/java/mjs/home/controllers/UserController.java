package mjs.home.controllers;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import mjs.model.User;

/**
 * REST service used to retrieve the current list of users.
 */

//@Autowired
@Controller
public class UserController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody User getUserList() {
/*
        StringBuilder builder = new StringBuilder()
        .append("[")
        .append("{'selected': false, 'user_pk': '1', 'username': 'mjshoemake'},")
        .append("{'selected': false, 'user_pk': '2', 'username': 'mrshoemake'},")
        .append("{'selected': false, 'user_pk': '3', 'username': 'cashoemake'}")
        .append("{'selected': false, 'user_pk': '4', 'username': 'heshoemake'}")
        .append("{'selected': false, 'user_pk': '5', 'username': 'izshoemake'}")
        .append("]");
        return builder.toString();
*/
        User user = new User();
        user.setSelected(new Boolean(false));
        user.setUser_pk(1);
        user.setUsername("mjshoemake");
        return user;

    }
}
