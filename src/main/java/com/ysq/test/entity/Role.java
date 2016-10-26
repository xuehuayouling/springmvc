package com.ysq.test.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ysq on 16/10/25.
 */
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "t_relation_role_user", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> users = new HashSet<>();
    @ManyToMany(targetEntity = Team.class)
    @JoinTable(name = "t_relation_role_team", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    Set<Team> teams = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
