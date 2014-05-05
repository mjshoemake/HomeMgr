package mjs.common.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mjs.common.core.SeerObject;
import mjs.common.exceptions.CoreException;
import mjs.common.exceptions.ModelException;
import mjs.common.utils.BeanUtils;
import mjs.common.utils.LogUtils;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * The base class for services which interact with the database through
 * Hibernate.
 */
@Transactional
public class BaseService extends SeerObject {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");
    protected static final Logger logResultSet = Logger.getLogger("ResultSet");

    @Autowired
    private SessionFactory sessionFactory;
    private String entityNameProperty;
    private String entityPkProperty;
    private String entityType;
    private String tableName;

    public BaseService(String entityClass,
                       String entityType,
                       String entityNameProperty,
                       String entityPkProperty,
                       String tableName) {
        super(entityClass);
        this.entityType = entityType;
        this.entityNameProperty = entityNameProperty;
        this.entityPkProperty = entityPkProperty;
        this.tableName = tableName;

        if (! LogUtils.isLoggingConfigured()) {
            LogUtils.initializeLogging();
        }
    }

    public Session openSession() {
        Session session = sessionFactory.openSession();
        session.setCacheMode(CacheMode.IGNORE);
        return session;
    }

    public Object getByPK(int id) throws ModelException {
        try {
            List<Object> entities = findByCriteria(Restrictions.eq(entityPkProperty, new Integer(id)));
            if (entities.size() == 1) {
                return entities.get(0);
            } else if (entities.size() > 1) {
                throw new Exception("More than one " + entityType + " returned matching this ID but only one is expected.");
            } else if (entities.size() == 0) {
                throw new Exception("No " + entityType + " found that match the specified ID.");
            } else {
                throw new Exception("Unexpected error. Number of items returned: " + entities.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this " + entityType + " (" + id + ").", e);
            throw new ModelException("Unable to retrieve this " + entityType + " (" + id + "). " + e.getMessage());
        }
    }

    public List getAll() throws ModelException {
        Session session = openSession();
        try {
            List result = session.createQuery("from " + tableName).list();
            log.debug("Service: getAll()  Type=" + tableName + "  Result: " + result.size());
            logResultSet.debug("Result Set:");
            LogUtils.debug(logResultSet, result, "   ", true);
            return result;
        } catch (Exception e) {
            log.error("Unable to retrieve the " + entityType + " list.", e);
            throw new ModelException("Unable to retrieve the " + entityType + " list. " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List findByCriteria(Criterion criterion) {
        Session session = openSession();
        try {
            Criteria criteria = session.createCriteria(entityClass);
            criteria.add(criterion);
            List result = criteria.list();
            return result;
        } finally {
            session.close();
        }
    }

    public Object findById(String id) {
        Session session = openSession();
        try {
            Object result = session.get(entityClass, id);
            return result;
        } finally {
            session.close();
        }
    }

    public void saveOrUpdate(Object entity) {
        Session session = openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public String save(Object entity) throws ModelException {
        try {
            if (entity != null) {
                Session session = openSession();
                Transaction tx = null;
                try {
                    Object name = getPropertyValue(entity, entityNameProperty);
                    log.debug("Saving " + entityType + " " + name + "...");
                    tx = session.beginTransaction();
                    String result = session.save(entity).toString();
                    tx.commit();
                    log.debug("   Saved.  Returning pk: " + result);
                    return result;
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    throw e;
                } finally {
                    session.close();
                }
            } else {
                throw new ModelException("Expected a valid " + entityType + " but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to save the specified " + entityType + ".", e);
            throw new ModelException("Unable to save the specified " + entityType + ". " + e.getMessage());
        }
    }

/*
    public void flush() {
        openSession().flush();
    }
*/

    public void update(Object entity) throws ModelException {
        try {
            if (entity != null) {
                Session session = openSession();
                Transaction tx = null;
                try {
                    Object name = getPropertyValue(entity, entityNameProperty);
                    log.debug("Updating " + entityType + " " + name + "...");
                    tx = session.beginTransaction();
                    session.update(entity);
                    tx.commit();
                    log.debug("   Update successful.");
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    throw e;
                } finally {
                    session.close();
                }
            } else {
                throw new ModelException("Expected a valid " + entityType + " but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to save the specified " + entityType + ".", e);
            throw new ModelException("Unable to save the specified " + entityType + ". " + e.getMessage());
        }
    }

    public void delete(Object entity) throws ModelException {
        try {
            if (entity != null) {
                Session session = openSession();
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.delete(entity);
                    tx.commit();
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    throw e;
                } finally {
                    session.close();
                }
            } else {
                throw new Exception("Expected a valid " + entityType + " but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this " + entityType + ".", e);
            throw new ModelException("Unable to delete this " + entityType + ". " + e.getMessage());
        }
    }

}
