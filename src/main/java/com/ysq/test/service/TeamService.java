package com.ysq.test.service;

import com.ysq.test.dao.RoleDAO;
import com.ysq.test.dao.TeamDAO;
import com.ysq.test.entity.Role;
import com.ysq.test.entity.Team;
import com.ysq.test.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ysq on 16/10/31.
 */
@Service
public class TeamService {
    @Autowired
    private TeamDAO teamDAO;

    public boolean isExist(String name, String parentId) {
        return teamDAO.queryByName(name, parentId) != null;
    }

    public boolean save(Team team) {
        return teamDAO.save(team);
    }

    public String getMaxCodeByParentId(String parentId) {
        return teamDAO.getMaxCodeByParentId(parentId);
    }

    public Team query(String id) {
        if (TextUtil.isEmpty(id)) return null;
        return teamDAO.queryById(id);
    }
}
