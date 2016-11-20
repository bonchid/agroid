package ru.sut.model.doi;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sut.model.company.Company;
import ru.sut.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "preffixes")
public class Preffix {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int registry;
    private int registrant;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "preffix")
    private List<Suffix> suffixes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Preffix(){

    }

    public Preffix(int registry, int registrant, Company company, User user) {
        this.registry = registry;
        this.registrant = registrant;
        this.company = company;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegistry() {
        return registry;
    }

    public void setRegistry(int registry) {
        this.registry = registry;
    }

    public int getRegistrant() {
        return registrant;
    }

    public void setRegistrant(int registrant) {
        this.registrant = registrant;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Suffix> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<Suffix> suffixes) {
        this.suffixes = suffixes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
