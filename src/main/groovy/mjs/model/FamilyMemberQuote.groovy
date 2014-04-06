 package mjs.model

 import mjs.common.model.ModelLoggable

 import javax.persistence.Entity
 import javax.persistence.GeneratedValue

 //import javax.persistence.Column;
 import javax.persistence.Id
 import javax.persistence.Table

 /**
 * This is the data object or suitcase for a FamilyMemberQuote. This data object
 * should not contain any business logic.
 */
 @Entity
 @Table(name="family_member_quotes")
class FamilyMemberQuote extends ModelLoggable {
    @Id
    @GeneratedValue
    int family_member_quotes_pk = -1;
    Date quote_date = "";
    String quote = "";
    int family_member_pk = -1;
}
