package ru.sut.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_groups")
public class UserGroup {

    @Id
    private int id;
    private String grp;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<User> users;

    public UserGroup() {
    }

    public UserGroup(String grp, List<User> users) {
        this.grp = grp;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrp() {
        return grp;
    }

    public void setGrp(String grp) {
        this.grp = grp;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
