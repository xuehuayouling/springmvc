package com.ysq.test.dao;

import com.ysq.test.util.ReflectionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.List;


/**
 * Dao层接口的公共实现类
 *
 * @param <T>  范型，指实体类
 * @author shenlx
 * @version 1.0
 */
public abstract class EntityDao<T extends Serializable> {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
	private JdbcTemplate jdbcTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 保存实体 包括添加和修改
     *
     * @param t 实体对象
     */
    public boolean save(T t) {
        return getSession().save(t) != null;
    }

    /**
     * 批量保存实体
     *
     * @param entitys 实体对象
     */
    public void save(T[] entitys, int batchSize) {
        Session session = getSession();
        if (null == entitys || entitys.length == 0) {
            return;
        }
        if (batchSize == 0) {
            batchSize = 10;
        }
        for (int i = 0; i < entitys.length; i++) {
            session.save(entitys[i]);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
    }

    /**
     * 保存实体 包括添加和修改
     *
     * @param t 实体对象
     */
    public void saveOrUpdate(T t) {
        getSession().saveOrUpdate(t);
    }

    public void saveOrUpdate(T[] entitys, int batchSize) {
        Session session = getSession();
        if (null == entitys || entitys.length == 0) {
            return;
        }
        if (batchSize == 0) {
            batchSize = 10;
        }
        for (int i = 0; i < entitys.length; i++) {
            session.saveOrUpdate(entitys[i]);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
    }


    /**
     * 删除实体
     *
     * @param t 实体对象
     */
    public void delete(T t) {
        getSession().delete(t);
    }

    /**
     * 批量删除对象
     **/
    public void deleteList(List<T> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                getSession().delete(t);
            }
        }
    }

    /**
     * 更新实体
     *
     * @param t 实体对象
     */
    public void update(T t) {
        Session session = getSession();
        session.update(t);
    }

    /**
     * 批量更新实体
     *
     * @param entitys 实体对象
     */
    public void update(T[] entitys, int batchSize) {
        Session session = getSession();
        if (null == entitys || entitys.length == 0) {
            return;
        }
        if (batchSize == 0) {
            batchSize = 10;
        }
        for (int i = 0; i < entitys.length; i++) {
            session.update(entitys[i]);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
    }

    /**
     * 更新实体
     *
     * @param t 实体对象
     */
    public void merge(T t) {
        getSession().merge(t);
    }

    /**
     * 批量更新实体
     *
     * @param entitys 实体对象
     */
    public void merge(T[] entitys, int batchSize) {
        Session session = getSession();
        if (null == entitys || entitys.length == 0) {
            return;
        }
        if (batchSize == 0) {
            batchSize = 10;
        }
        for (int i = 0; i < entitys.length; i++) {
            session.merge(entitys[i]);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
    }

    public T queryById(String id) {
        return (T) getSession().get(ReflectionUtils.getSuperClassGenricType(getClass()), id);
    }


}
