package mjs.home.services;

import mjs.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class MealsService extends BaseService {

    public MealsService() {
        super("mjs.model.Meal", "meal", "name", "meals_pk", "Meal");
    }
}
