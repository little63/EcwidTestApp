package ru.panov.testapp.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PRODUCT.
 */
public class Product {

    private Long id;
    private String tittle;
    private Float price;
    private Integer count;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(Long id, String tittle, Float price, Integer count) {
        this.id = id;
        this.tittle = tittle;
        this.price = price;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
