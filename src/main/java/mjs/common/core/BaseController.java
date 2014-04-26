package mjs.common.core;

import mjs.common.exceptions.ModelException;
import mjs.model.PrimaryKey;
import mjs.model.ServiceResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * REST service used to retrieve, update, and delete user data
 * from the database.
 */
@Controller
public class BaseController extends SeerObject {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Controller");

    private final String entityType;
    private String entityNameProperty;
    private String entityPkProperty;
    private final String tableName;

    public BaseController(String entityClass,
                          String entityType,
                          String entityNameProperty,
                          String entityPkProperty,
                          String tableName) {
        super(entityClass);
        log.debug(this.getClass().getName() + "  Constructor()");
        this.entityType = entityType;
        this.tableName = tableName;
        this.entityNameProperty = entityNameProperty;
        this.entityPkProperty = entityPkProperty;
    }


    public Object getList(Model model, BaseService service) {
        try {
            log.debug("REST Call: get" + tableName + "List()");
            return service.getAll();
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving the " + entityType + " list. " + e.getMessage());
        }
    }

    public Object getByPK(Model model, int pk, BaseService service) {
        try {
            log.debug("REST Call: get" + tableName + "ByPK(pk=" + pk + ")");
            return service.getByPK(pk);
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred retrieving " + entityType + " " + pk + ". " + e.getMessage());
        }
    }

    public Object delete(Model model, int pk, BaseService service) {
        try {
            log.debug("REST Call: delete" + tableName + "Cookbook(pk=" + pk + ")");
            Object entityToDelete = service.getByPK(pk);
            String entityName = getPropertyValue(entityToDelete, entityNameProperty) + "";
            log.debug("   Deleting " + entityType + " " + entityName + "...");
            service.delete(entityToDelete);
            log.debug("   Deleting " + entityType + " " + entityName + "... Done.");
            return new ServiceResponse(200, "Success", "Successfully deleted " + entityType + " " + entityName + ".");
        } catch (ModelException e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred deleting " + entityType + " " + pk + ". " + e.getMessage());
        }
    }

    public Object update(Model model, Object entity, BaseService service) {
        String name = null;
        try {
            name = getPropertyValue(entity, entityNameProperty);
            if (getPropertyValue(entity, entityPkProperty).equals("0")) {
                String newPk = service.save(entity);
                return new PrimaryKey(newPk);
            } else {
                service.update(entity);
                return new ServiceResponse(200, "Success", "Successfully updated " + entityType + " " + name + ".");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ServiceResponse(500, "Error", "An error occurred updating " + entityType + " " + name + ". " + e.getMessage());
        }
    }

}
