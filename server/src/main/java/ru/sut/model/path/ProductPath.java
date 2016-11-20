package ru.sut.model.path;


import com.fasterxml.jackson.annotation.*;
import ru.sut.model.company.Company;
import ru.sut.model.doi.Suffix;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "products_paths")
public class ProductPath {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "suffix_id")
    private Suffix suffix;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "next_company_id", referencedColumnName = "id")
    private ProductPath productPath;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "productPath")
    private List<ProductPath> productPathList;

    public ProductPath() {
    }

    public ProductPath(Suffix suffix, Company company, ProductPath productPath) {
        this.suffix = suffix;
        this.company = company;
        this.productPath = productPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Suffix getSuffix() {
        return suffix;
    }

    public void setSuffix(Suffix suffix) {
        this.suffix = suffix;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ProductPath getProductPath() {
        return productPath;
    }

    public void setProductPath(ProductPath productPath) {
        this.productPath = productPath;
    }

    public List<ProductPath> getProductPathList() {
        return productPathList;
    }

    public void setProductPathList(List<ProductPath> productPathList) {
        this.productPathList = productPathList;
    }
}
