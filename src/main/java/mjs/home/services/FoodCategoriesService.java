package mjs.home.services;

import mjs.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class FoodCategoriesService extends BaseService {

    public FoodCategoriesService() {
        super("mjs.model.FoodCategory", "food category", "name", "meal_categories_pk", "FoodCategory");
    }
}
