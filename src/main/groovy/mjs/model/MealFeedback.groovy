 package mjs.model

 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity
 import javax.persistence.GeneratedValue
 import javax.persistence.Id

 //import javax.persistence.Column;
 import javax.persistence.Table

 /**
 * This is the data object or suitcase for a Meal. This data object
 * should not contain any business logic.
 */
 @Entity
 @Table(name="meal_feedback")
class MealFeedback extends ModelLoggable {
     @Id
     @GeneratedValue
     int meal_feedback_pk = -1
     int user_pk = -1
     int fork_rating = -1
     String comments = ""
 }
