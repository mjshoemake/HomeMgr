package mjs.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjs.common.database.PaginatedList;
import mjs.common.database.TableDataManager;
import mjs.common.exceptions.ActionException;
import mjs.common.model.Form;
import mjs.common.utils.Constants;
import mjs.common.view.ValidationErrorList;
import mjs.struts.AbstractAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class UpdateFamilyMemberAction extends AbstractAction {
    /**
     * Execute this action.
     * 
     * @param mapping
     * @param form
     * @param req Description of Parameter
     * @param res Description of Parameter
     * @return ActionForward
     * @exception Exception Description of Exception
     */
    public ActionForward processRequest(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {
        metrics.startEvent("UpdateFamilyMember", "action");
        DynaActionForm actionForm = (DynaActionForm)form;

        try {
            TableDataManager dbMgr = getTable("FamilyMemberMapping.xml");
            ValidationErrorList errors = dbMgr.validateForm((Form)form);
            if (errors.isEmpty()) {
                log.debug("Form validated successfully.");
                // Update recipe.
                try {
                    dbMgr.open();
                    String whereClause = "where family_member_pk = " + actionForm.getString("family_member_pk");
                    dbMgr.updateBean(form, whereClause);
                    dbMgr.close(true);
                } catch (Exception e) {
                    dbMgr.close(false);
                    throw e;
                }

                // PaginatedList is now dirty.
                req.getSession().setAttribute(Constants.ATT_PAGINATED_LIST_DIRTY, "true");

                // Get the paginated list.
                PaginatedList list = (PaginatedList) req.getSession()
                    .getAttribute(Constants.ATT_PAGINATED_LIST_CACHE);
                return (mapping.findForward(list.getGlobalForward()));
            } else {
                // Validation failed.
                log.debug("Form validation failed.");
                return (mapping.findForward("invalid"));
            }
        } catch (java.lang.Exception e) {
            ActionException ex = new ActionException("Error trying to save a new recipe.", e);
            throw ex;
        } finally {
            metrics.endEvent("UpdateFamilyMember", "action");
            metrics.writeMetricsToLog();
        }
    }

}
