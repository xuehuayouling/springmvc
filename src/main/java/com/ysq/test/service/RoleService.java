package com.ysq.test.service;

import com.ysq.test.dao.RoleDAO;
import com.ysq.test.dao.TeamDAO;
import com.ysq.test.dao.UserDAO;
import com.ysq.test.entity.Role;
import com.ysq.test.entity.Team;
import com.ysq.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private TeamDAO teamDAO;

    public Role queryById(long id) {
        return roleDAO.queryById(id);
    }

    public List<User> queryUserById(long roleId) {
        Role role = queryById(roleId);
        if (role == null) {
            return null;
        }
        Set<Team> teamSet = new HashSet<>();
        if (role != null) {
            Set<Team> teams = role.getTeams();
            if (teams != null) {
                for (Team team: teams) {
                    List<Team> teams1 = teamDAO.queryUserById(team.getId(), true);
                    if (teams1 != null) {
                        teamSet.addAll(teams1);
                    }
                }
            }
        }
        Set<User> userSet = new HashSet<>(role.getUsers());
        if (teamSet != null && teamSet.size() > 0) {
            for (Team team : teamSet) {
                userSet.addAll(team.getUsers());
            }
        }
        List<User> users = new ArrayList<>(userSet);
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getId() > o2.getId() ? 1 : o1.getId() < o2.getId() ?  -1 : 0;
            }
        });
        return users;
    }
}
