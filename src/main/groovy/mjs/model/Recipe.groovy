 package mjs.model

 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity

 //import javax.persistence.Column;
 import javax.persistence.GeneratedValue
 import javax.persistence.Id
 import javax.persistence.Table

 /**
 * This is the data object or suitcase for a Recipe. This data object
 * should not contain any business logic.
 */
 @Entity
 @Table(name="recipes")
class Recipe extends ModelLoggable {
     @Id
     @GeneratedValue
     int recipes_pk = -1
     String name = ""
     String servings = ""
     int cookbook_pk = -1
     int meals_pk = -1
     int meal_categories_pk = -1
     String nutrition = ""
     String picture_filename = ""
     String notes = ""
     int created_by = -1
     Integer calories_per_serving = 0
     String ingredients = ""
     String directions = ""
     String serving_size = ""
     String favorite = ""
     def selected = false
 }
