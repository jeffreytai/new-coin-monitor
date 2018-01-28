package com.crypto.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ExchangeMarket")
public class ExchangeMarket {

    /**
     * Auto-generated Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    /**
     * Foreign key to Exchange Id
     */
    @Column(name = "ExchangeId")
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_ExchangeId"))
    private Long exchangeId;

    /**
     * Name of market
     */
    @Column(name = "Name", nullable = false)
    private String name;

    /**
     * Trading pair
     */
    @Column(name = "Pair", nullable = false)
    private String pair;

    /**
     * Creation date for entity
     */
    @Column(name = "Created")
    private Date created;

    /**
     * Constructors
     */
    public ExchangeMarket() {}

    public ExchangeMarket(Long exchangeId, String name, String pair) {
        this.exchangeId = exchangeId;
        this.name = name;
        this.pair = pair;
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

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
