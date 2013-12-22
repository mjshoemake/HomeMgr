 package mjs.model;

import mjs.common.model.ModelLoggable;

/**
 * This is the data object or suitcase for a User. This data object
 * should not contain any business logic. This class is used by the
 * AuthenticationManager.
 */
class User extends ModelLoggable {
   /**
    * The user ID for this user. This is how users should be
    * referenced in the database.
    */
   int user_pk = -1;

   /**
    * The user's first name.
    */
   String fname = "";

   /**
    * The user's last name.
    */
   String lname = "";

   /**
    * The user login ID.
    */
   String username = "";

   /**
    * The user's password.
    */
   String password = "";

   /**
    * The user's password.
    */
   String pw = "";

   /**
    * Is the login enabled for this user?
    */
   String login_enabled = "Y";

}
