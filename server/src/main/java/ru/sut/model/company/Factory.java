package ru.sut.model.company;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sut.model.company.Company;
import ru.sut.model.doi.Suffix;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "factories")
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "factory")
    private List<Suffix> suffixes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Factory() {
    }

    public Factory(String address, List<Suffix> suffixes, Company company) {
        this.address = address;
        this.suffixes = suffixes;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Suffix> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<Suffix> suffixes) {
        this.suffixes = suffixes;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
