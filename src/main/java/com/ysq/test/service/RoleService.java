package com.ysq.test.service;

import com.ysq.test.dao.RoleDAO;
import com.ysq.test.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ysq on 16/10/31.
 */
@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;

    public boolean isExist(String name) {
        return roleDAO.queryByName(name) != null;
    }

    public boolean save(Role role) {
        return roleDAO.save(role);
    }
}
