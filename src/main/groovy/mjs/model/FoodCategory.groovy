 package mjs.model

 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity
 import javax.persistence.GeneratedValue
 import javax.persistence.Id
 import javax.persistence.Table

 //import javax.persistence.Column;
/**
 * This is the data object or suitcase for a FoodCategory. This data object
 * should not contain any business logic.
 */
 @Entity
 @Table(name="meal_categories")
class FoodCategory extends ModelLoggable {
     @Id
     @GeneratedValue
     int meal_categories_pk = -1
     String name = ""
 }
