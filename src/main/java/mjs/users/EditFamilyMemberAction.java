package mjs.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjs.common.database.DataManager;
import mjs.common.exceptions.ActionException;
import mjs.common.utils.Constants;
import mjs.struts.AbstractAction;
import mjs.struts.DynaForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditFamilyMemberAction extends AbstractAction {

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
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
      metrics.startEvent("EditRecipe", "action");
      try {
         DataManager dbMgr = new DataManager(getDriver()); 
 
         String pk = req.getParameter(Constants.PARAM_ID);
         addBreadcrumbs(req, "Edit Family Member", "../EditFamilyMember.do?id=" + pk);

         // Validate form data.
         log.debug("DataMapping file loaded.");
         if (pk != null) {
            log.debug("Form validated successfully.");
            // Insert recipe.
            try {
               dbMgr.open();
               dbMgr.loadBean(form, 
                              "family_members", 
                              DynaForm.class, 
                              "/mjs/users/FamilyMemberMapping.xml",
                              " where family_member_pk = " + pk);
            }
            catch (Exception e) {
               throw e;
            } finally {
                dbMgr.close();
            }

            return (mapping.findForward("success"));
         } else {
            // Validation failed.
            log.debug("Primary key is missing or invalid.");
            return (mapping.findForward("invalid"));
         }
      }
      catch (java.lang.Exception e) {
         ActionException ex = new ActionException("Error trying to save a new recipe.", e);
         throw ex;
      }
      finally {
         metrics.endEvent("EditRecipe", "action");
         metrics.writeMetricsToLog();
      }
   }

}
