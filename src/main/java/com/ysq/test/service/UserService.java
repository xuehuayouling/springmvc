package com.ysq.test.service;

import com.ysq.test.dao.TeamDAO;
import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.Team;
import com.ysq.test.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.UserDAO;
import com.ysq.test.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDAO userDao;

    public User login(String name, String password, String sessionID) {
        User user = userDao.queryByNameAndPassword(name, password);
        if (user != null) {
            return addToken(user, sessionID);
        }
        return null;
    }

    private User addToken(User user, String sessionID) {
        return userDao.addToken(user, sessionID);
    }

    public boolean addUser(User user) {
        return userDao.save(user);
    }

    public boolean isExist(String name) {
        return userDao.queryByName(name) != null;
    }

    public User queryByToken(String token) {
        return userDao.queryByToken(token);
    }

//	public List<User> queryUserByRoleId(long roleId) {
//		Role role = roleDAO.queryById(roleId);
//		if (role == null) {
//			return null;
//		}
//		Set<Team> teams = role.getTeams();
//		Map<Long, User> userMap = new HashMap<>();
//		Set<User> userSet = role.getUsers();
//		if (userSet != null && userSet.size() > 0) {
//			for (User user : userSet) {
//				userMap.put(user.getId(), user);
//			}
//		}
//		if (teams != null && teams.size() > 0) {
//			for (Team team : teams) {
//				List<User> users = teamDAO.queryUserById(team.getId(), true);
//				if (users != null && users.size() > 0) {
//					for (User user : users) {
//						userMap.put(user.getId(), user);
//					}
//				}
//			}
//		}
//		List<User> users = new ArrayList<>(userMap.values());
//		Collections.sort(users, new Comparator<User>() {
//			@Override
//			public int compare(User o1, User o2) {
//				return o1.getId() > o2.getId() ? 1 : o1.getId() < o2.getId() ?  -1 : 0;
//			}
//		});
//		return users;
//	}
}
