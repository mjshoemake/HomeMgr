package mjs.home.services;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The base class for services which interact with the database through
 * Hibernate.
 */
 @Transactional
public class BaseService {

    @Autowired
    private SessionFactory sessionFactory;
    private Class entityClass;

    public BaseService(Class entityClass) {
        this.entityClass = entityClass;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    List getAll(String query) {
        return sessionFactory.getCurrentSession().createQuery(query).list();
    }

    public List findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }

    public Object findById(String id) {
        return getCurrentSession().get(entityClass, id);
    }

    public void saveOrUpdate(Object entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    public String save(Object entity) {
        return getCurrentSession().save(entity).toString();
    }

    public void update(Object entity) {
        getCurrentSession().update(entity);
    }

    public void delete(Object entity) {
        getCurrentSession().delete(entity);
    }

}
