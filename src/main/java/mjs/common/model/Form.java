package mjs.common.model;

import mjs.common.aggregation.OrderedMap;
import mjs.common.exceptions.ValidationException;
import mjs.common.view.ValidationErrorList;


/**
 * Struts action form class which is the base ActionForm class for
 * Struts applications. It includes a Log4J mjs, so
 * log messages can be sent without instantiating the Logger object.
 */
public interface Form {

	/**
	 * Check to see if this form is valid.
	 * 
	 * @param mapping Description of Parameter
	 * @return ValidationErrorList
	 */
    public ValidationErrorList validate(OrderedMap mapping) throws ValidationException;
}
