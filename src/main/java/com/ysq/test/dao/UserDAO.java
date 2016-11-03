package com.ysq.test.dao;

import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.Team;
import com.ysq.test.entity.User;
import com.ysq.test.util.TextUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAO extends EntityDao<User> {

    public User addToken(User user, String sessionID) {
        if (user != null) {
            user.setToken(sessionID);
            saveOrUpdate(user);
            return user;
        }
        return null;
    }

    public User queryByName(String name) {
        if (TextUtil.isEmpty(name)) {
            return null;
        }
        String sql = " from User u where u.name=:name";
        return (User) getSession().createQuery(sql).setParameter("name", name).uniqueResult();
    }

    public User queryByNameAndPassword(String name, String password) {
        if (TextUtil.isEmpty(name)) {
            return null;
        }
        String sql = " from User u where u.name=:name and u.password=:password";
        return (User) getSession().createQuery(sql).setParameter("name", name).setParameter("password", password).uniqueResult();
    }

    public User queryByToken(String token) {
        if (TextUtil.isEmpty(token)) {
            return null;
        }
        String sql = " from User u where u.token=:token";
        return (User) getSession().createQuery(sql).setParameter("token", token).uniqueResult();
    }

}
