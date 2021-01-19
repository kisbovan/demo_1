package com.example.restservice.models;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="gamer_credit")
public class Credit {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull(message="Gamer Id is mandatory")
    private long gamerId;

    @NotNull(message="Game Id is mandatory")
    private long gameId;

    @NotNull(message="Credit is mandatory")
    private int credit;

    // default constructor
    public Credit() {}

    public Credit(long gamerId, long gameId, int credit) {
        super();
        this.gamerId = gamerId;
        this.gameId = gameId;
        this.credit = credit;
    }

    public long getGamerId() {
        return gamerId;
    }

    public void setGamerId(long id) {
        this.gamerId = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long id) {
        this.gameId = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}