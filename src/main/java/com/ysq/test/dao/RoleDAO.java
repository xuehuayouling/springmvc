package com.ysq.test.dao;

import com.ysq.test.entity.Role;
import com.ysq.test.entity.User;
import com.ysq.test.util.TextUtil;
import org.springframework.stereotype.Repository;

/**
 * Created by ysq on 16/10/31.
 */
@Repository
public class RoleDAO extends EntityDao<Role> {

    public Role queryByName(String name) {
        if (TextUtil.isEmpty(name)) {
            return null;
        }
        String sql = " from Role r where r.name=:name";
        return (Role) getSession().createQuery(sql).setParameter("name", name).uniqueResult();
    }
}
