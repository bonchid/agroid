package ru.sut.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sut.model.doi.Suffix;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String productName;
    private String info;

    //Shelf time of product in days
    private int shelfTime;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Suffix> suffixes;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Product() {
    }

    public Product(String productName, String info, int shelfTime, Company company) {
        this.productName = productName;
        this.info = info;
        this.shelfTime = shelfTime;
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(int shelfTime) {
        this.shelfTime = shelfTime;
    }

    public List<Suffix> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<Suffix> suffixes) {
        this.suffixes = suffixes;
    }
}
