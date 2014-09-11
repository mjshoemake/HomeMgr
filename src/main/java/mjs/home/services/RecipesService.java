package mjs.home.services;

import mjs.common.core.BaseService;
import mjs.common.exceptions.ModelException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class RecipesService extends BaseService {

    public RecipesService() {
        super("mjs.model.Recipe", "recipe", "name", "recipes_pk", "mjs.model.Recipe");
    }

}
