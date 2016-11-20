package ru.sut.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sut.model.doi.Preffix;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String login;
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup group;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Preffix> preffixes;

    public User() {
    }

    public User(String login, String password, UserGroup group) {
        this.login = login;
        this.password = password;
        this.group = group;
    }

    public List<Preffix> getPreffixes() {
        return preffixes;
    }

    public void setPreffixes(List<Preffix> preffixes) {
        this.preffixes = preffixes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
}
