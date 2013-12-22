package mjs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import mjs.common.aggregation.OrderedMap;
import mjs.common.model.BusinessObject;
import mjs.common.view.ValidationErrorList;

/**
 * This is the data object or suitcase for a Cookbook. This data object should
 * not contain any business logic.
 */
public @Data @EqualsAndHashCode(callSuper=true) 
    class CookbookForm extends AbstractForm implements BusinessObject {
	
    static final long serialVersionUID = -4174504602386548113L;

	/**
     * The name.
	 */
	private String name = "";
	    
	
	/**
	 * The primary key. This is how users should be referenced in the
	 * database.
	 */
	private String cookbooks_pk = "";

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public String getPk() {
		return cookbooks_pk;
	}

	/**
	 * The primary key. Implemented from BusinessObject interface which allows
	 * this object to be used in conjunction with PaginatedList.
	 */
	public void setPk(String value) {
		cookbooks_pk = value;
	}

	/**
     * The name.
	 */
	public void setName(String value) {
		name = value;
	}

	/**
     * The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Check to see if this form is valid.
	 * 
	 * @param mapping
	 *            Description of Parameter
	 * @return ValidationErrorList
	 */
	public ValidationErrorList validate(OrderedMap mapping) {
		ValidationErrorList errors = new ValidationErrorList();
		return errors;
	}

}
