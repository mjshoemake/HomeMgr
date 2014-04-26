package mjs.home.services;

import mjs.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class RecipesService extends BaseService {

    public RecipesService() {
        super("mjs.model.Recipe", "recipe", "name", "recipes_pk", "Recipe");
    }
}
