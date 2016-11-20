package ru.sut.model.doi;


import ru.sut.model.company.Factory;
import ru.sut.model.company.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "suffixes")
public class Suffix {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String suffix;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "preffix_id")
    private Preffix preffix;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String manufactureDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;

    public Suffix() {
    }

    public Suffix(String suffix, Preffix preffix, Product product, String manufactureDate, Factory factory) {
        this.suffix = suffix;
        this.preffix = preffix;
        this.product = product;
        this.manufactureDate = manufactureDate;
        this.factory = factory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Preffix getPreffix() {
        return preffix;
    }

    public void setPreffix(Preffix preffix) {
        this.preffix = preffix;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }
}
