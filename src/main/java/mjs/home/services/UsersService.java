package mjs.home.services;

import mjs.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class UsersService extends BaseService {

    public UsersService() {
        super("mjs.model.User", "users", "fname+lname", "user_pk", "User");
    }
}
