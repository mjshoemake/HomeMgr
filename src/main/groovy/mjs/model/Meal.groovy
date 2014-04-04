 package mjs.model

 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity
 import javax.persistence.GeneratedValue

 //import javax.persistence.Column;
 import javax.persistence.Id
 import javax.persistence.Table

 /**
 * This is the data object or suitcase for a Meal. This data object
 * should not contain any business logic.
 */
 @Entity
 @Table(name="meals")
class Meal extends ModelLoggable {
     @Id
     @GeneratedValue
     int meals_pk = -1
     String name = ""
 }
