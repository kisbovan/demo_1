package com.example.restservice.models;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="gamer_levels")
public class GamerLevel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull(message="Gamer Id is mandatory")
    private long gamerId;

    @NotNull(message="Level Id is mandatory")
    private long levelId;

    @NotNull(message="Game Id is mandatory")
    private long gameId;

    // default constructor
    public GamerLevel() {}

    public GamerLevel(long gamerId, long levelId, long gameId) {
        super();
        this.gamerId = gamerId;
        this.levelId = levelId;
        this.gameId = gameId;
    }

    public long getId() {
        return id;
    }

    public long getGamerId() {
        return gamerId;
    }

    public void setgamerId(long gamerId) {
        this.gamerId = gamerId;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}