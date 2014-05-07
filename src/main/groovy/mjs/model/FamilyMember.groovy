 package mjs.model

 import com.fasterxml.jackson.annotation.JsonIgnoreProperties
 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity

 //import javax.persistence.Column;
 import javax.persistence.GeneratedValue
 import javax.persistence.Id
 import javax.persistence.Table
 import javax.persistence.Transient

 /**
 * This is the data object or suitcase for a FamilyMember. This data object
 * should not contain any business logic. This class is used by the
 * AuthenticationManager.
 */
 @Entity
 @Table(name="family_members")
 @JsonIgnoreProperties(ignoreUnknown = true)
class FamilyMember extends ModelLoggable {
   @Id
   @GeneratedValue
   int family_member_pk = -1
   String fname = ""
   String lname = ""
   String description = ""
   Date dob = null

     @Transient
     public getName() {
         if (fname != null && ! fname.equals("") && lname != null && lname.equals("")) {
             return fname + " " + lname;
         } else if (fname != null && ! fname.equals("")) {
             return fname;
         } else if (lname != null && lname.equals("")) {
             return lname;
         } else {
             return "";
         }
     }
 }
