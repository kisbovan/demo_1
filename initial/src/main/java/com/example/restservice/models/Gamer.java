package com.example.restservice.models;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="gamers")
public class Gamer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull(message="Name is mandatory")
    private String name;

    @NotNull(message="Gender Id is mandatory")
    private long genderId;

    @NotNull(message="Nickname is mandatory")
    private String nickname;

    @NotNull(message="Geography Id is mandatory")
    private long geographyId;

    // default constructor
    public Gamer() {}

    public Gamer(String name, long genderId, String nickname, long geographyId) {
        super();
        this.name = name;
        this.genderId = genderId;
        this.nickname = nickname;
        this.geographyId = geographyId;
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

    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getGeographyId() {
        return geographyId;
    }

    public void setGeographyId(long geographyId) {
        this.geographyId = geographyId;
    }
}