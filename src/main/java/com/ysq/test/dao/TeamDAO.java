package com.ysq.test.dao;

import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.Role;
import com.ysq.test.entity.Team;
import com.ysq.test.util.TextUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ysq on 16/10/31.
 */
@Repository
public class TeamDAO extends EntityDao<Team> {

    public Team queryByName(String name, String parentId) {
        if (TextUtil.isEmpty(name)) {
            return null;
        }
        String sql = " from Team t where t.name=:name ";
        if (parentId != null) {
            sql += "and t.parentId=:parentId";
        }
        Query query = getSession().createQuery(sql).setParameter("name", name);
        if (parentId != null) {
            query = query.setParameter("parentId", parentId);
        }
        return (Team) query.uniqueResult();
    }

    public String getMaxCodeByParentId(String parentId) {
        String sql = "select max(t.code) code from t_team t";
        if (!TextUtil.isEmpty(parentId)) {
            sql = sql + " where t.parent_id='" + parentId + "'";
        }
        String code = getJdbcTemplate().queryForObject(sql, String.class);
        return code;
    }

    public List<Team> queryByUserId(String userId) {
        return null;
    }
}
