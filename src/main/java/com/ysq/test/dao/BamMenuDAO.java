package com.ysq.test.dao;

import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.Team;
import com.ysq.test.util.TextUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ysq on 16/10/31.
 */
@Repository
public class BamMenuDAO extends EntityDao<BamMenu> {

    public BamMenu queryByName(String name, String parentId) {
        if (TextUtil.isEmpty(name)) {
            return null;
        }
        String sql = " from BamMenu t where t.name=:name ";
        if (parentId != null) {
            sql += "and t.parentId=:parentId";
        }
        Query query = getSession().createQuery(sql).setParameter("name", name);
        if (parentId != null) {
            query = query.setParameter("parentId", parentId);
        }
        return (BamMenu) query.uniqueResult();
    }

    public String getMaxCodeByParentId(String parentId) {
        String sql = "select max(t.code) code from t_bam_menu t";
        if (!TextUtil.isEmpty(parentId)) {
            sql = sql + " where t.parent_id='" + parentId + "'";
        }
        String code = getJdbcTemplate().queryForObject(sql, String.class);
        return code;
    }

}
