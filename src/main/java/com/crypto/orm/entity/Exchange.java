package com.crypto.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Exchange")
public class Exchange {

    /**
     * Auto-generated Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    /**
     * Name of exchange
     */
    @Column(name = "Name", nullable = false)
    private String name;

    /**
     * Url to exchange on CoinMarketCap
     */
    @Column(name = "Url")
    private String url;

    /**
     * Creation date for entity
     */
    @Column(name = "Created")
    private Date created;


    /**
     * Constructors
     */
    public Exchange() {}

    public Exchange(String name, String url) {
        this.name = name;
        this.url = url;
        this.created = new Date();
    }

    /**
     * Getters and setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
