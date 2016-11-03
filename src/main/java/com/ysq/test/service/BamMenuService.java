package com.ysq.test.service;

import com.ysq.test.dao.BamMenuDAO;
import com.ysq.test.dao.RoleDAO;
import com.ysq.test.dao.TeamDAO;
import com.ysq.test.dao.UserDAO;
import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.Role;
import com.ysq.test.entity.Team;
import com.ysq.test.entity.User;
import com.ysq.test.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ysq on 16/10/31.
 */
@Service
public class BamMenuService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private BamMenuDAO bamMenuDAO;
    @Autowired
    private RoleDAO roleDAO;

    public boolean isExist(String name, String parentId) {
        return bamMenuDAO.queryByName(name, parentId) != null;
    }

    public boolean save(BamMenu bamMenu) {
        Role role = roleDAO.queryById("cb5b7b4d1ce7450a83c5caee4664aebc");
        bamMenu.getRoleList().add(role);
        return bamMenuDAO.save(bamMenu);
    }

    public String getMaxCodeByParentId(String parentId) {
        return bamMenuDAO.getMaxCodeByParentId(parentId);
    }

    public BamMenu query(String id) {
        if (TextUtil.isEmpty(id)) return null;
        return bamMenuDAO.queryById(id);
    }

    public List<BamMenu> queryBamMenuByUserId(String userId) {
        Set<String> roleSet = new HashSet<>();
        String sqlForRoleByUser = "SELECT r.id FROM t_role r, t_user u, t_relation_role_user rru WHERE r.id=rru.role_id AND u.id=rru.user_id and u.id='%s'";
        sqlForRoleByUser = String.format(sqlForRoleByUser, userId);
        List<String> roleIds = userDAO.getJdbcTemplate().queryForList(sqlForRoleByUser, String.class);
        if (roleIds != null && roleIds.size() > 0) {
            roleSet.addAll(roleIds);
        }
        String sqlForTeamByUser = "SELECT t.* from t_team t, t_user u, t_relation_user_team rut WHERE t.id=rut.team_id and u.id=rut.user_id and u.id='%s'";
        sqlForTeamByUser = String.format(sqlForTeamByUser, userId);
        List<Map<String, Object>> teamIds = teamDAO.getJdbcTemplate().queryForList(sqlForTeamByUser);
        Set<String> teamSet = new HashSet<>();
        if (teamIds != null && teamIds.size() > 0) {
            for (Map<String, Object> teamMap : teamIds) {
                Team team = new Team();
                team.setId((String) teamMap.get("id"));
                team.setParentId((String) teamMap.get("parent_id"));
                team.setCode((String) teamMap.get("code"));
                team.setName((String) teamMap.get("name"));
                teamSet.add(team.getId());
                while (!TextUtil.isEmpty(team.getParentId())) {
                    team = teamDAO.queryById(team.getParentId());
                    teamSet.add(team.getId());
                }
            }
        }
        if (teamSet.size() > 0) {
            for (String teamId : teamSet) {
                String sqlForRoleByTeam = "SELECT r.id FROM t_role r, t_team t, t_relation_role_team rrt WHERE r.id = rrt.role_id and t.id = rrt.team_id and t.id='%s'";
                sqlForRoleByTeam = String.format(sqlForRoleByTeam, teamId);
                roleIds = userDAO.getJdbcTemplate().queryForList(sqlForRoleByTeam, String.class);
                if (roleIds != null && roleIds.size() > 0) {
                    roleSet.addAll(roleIds);
                }
            }
        }
        Set<BamMenu> bamMenuSet = new HashSet<>();
        if (roleSet.size() > 0) {
            for (String roleId : roleSet) {
                String sqlForBamMenuByRole = "SELECT bm.* FROM t_bam_menu bm, t_role r, t_relation_role_bam_menu rrbm WHERE bm.id=rrbm.bam_menu_id and r.id=rrbm.role_id and r.id='%s'";
                sqlForBamMenuByRole = String.format(sqlForBamMenuByRole, roleId);
                List<Map<String, Object>> menus = userDAO.getJdbcTemplate().queryForList(sqlForBamMenuByRole);
                if (menus != null && menus.size() > 0) {
                    for (Map<String, Object> menuMap : menus) {
                        BamMenu bamMenu = new BamMenu();
                        bamMenu.setId((String) menuMap.get("id"));
                        bamMenu.setParentId((String) menuMap.get("parent_id"));
                        bamMenu.setCode((String) menuMap.get("code"));
                        bamMenu.setName((String) menuMap.get("name"));
                        bamMenuSet.add(bamMenu);
                    }
                }

            }
        }
        return new ArrayList<>(bamMenuSet);
    }
}
