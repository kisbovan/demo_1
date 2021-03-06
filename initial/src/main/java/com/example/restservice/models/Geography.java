package com.example.restservice.models;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="geography")
public class Geography {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull(message="Name Id is mandatory")
    private String name;

    // default constructor
    public Geography() {}

    public Geography(String name) {
        super();
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}