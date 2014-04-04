 package mjs.model

 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity

 //import javax.persistence.Column;
 import javax.persistence.GeneratedValue
 import javax.persistence.Id
 import javax.persistence.Table

 /**
 * This is the data object or suitcase for a FamilyMember. This data object
 * should not contain any business logic. This class is used by the
 * AuthenticationManager.
 */
 @Entity
 @Table(name="family_members")
class FamilyMember extends ModelLoggable {
   @Id
   @GeneratedValue
   int family_member_pk = -1
   String name = ""
   String fname = ""
   String lname = ""
   String description = ""
   Date dob = null
}
