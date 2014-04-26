package mjs.home.services;

import mjs.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class CookbooksService extends BaseService {

    public CookbooksService() {
        super("mjs.model.Cookbook", "cookbook", "name", "cookbooks_pk", "Cookbook");
    }
}
