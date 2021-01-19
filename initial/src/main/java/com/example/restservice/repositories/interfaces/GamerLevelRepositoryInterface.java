package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.GamerLevel;

import java.util.ArrayList;
import java.util.Optional;

public interface GamerLevelRepositoryInterface extends CrudRepository<GamerLevel, Long> {

    public ArrayList<GamerLevel> findByGamerId(long id);

    public Optional<GamerLevel> findByGamerIdAndGameId(long gamerId, long gameId);

    public ArrayList<GamerLevel> findByLevelId(long levelId);

    public ArrayList<GamerLevel> findByGameIdAndLevelId(long gameId, long levelId);

    public Optional<GamerLevel> findByGamerIdAndGameIdAndLevelId(long gamerId, long gameId, long levelId);
}