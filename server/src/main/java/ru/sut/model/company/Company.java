package ru.sut.model.company;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sut.model.doi.Preffix;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Preffix> preffixes;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Product> products;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Factory> factories;

    public Company() {
    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Company(String name, String address, List<Preffix> preffixes, List<Product> products, List<Factory> factories) {
        this.name = name;
        this.address = address;
        this.preffixes = preffixes;
        this.products = products;
        this.factories = factories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Preffix> getPreffixes() {
        return preffixes;
    }

    public void setPreffixes(List<Preffix> preffixes) {
        this.preffixes = preffixes;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }
}
