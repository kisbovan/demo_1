package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.GamerLevelRepositoryInterface;
import com.example.restservice.repositories.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.GamerLevel;
import com.example.restservice.models.Gamer;
import java.lang.Iterable;
import java.util.ArrayList;

import java.util.Optional;

@Service
public class GamerLevelRepository {

    @Autowired
    private GamerLevelRepositoryInterface gamerLevelRepo;

    @Autowired
    private GamerRepository gamerRepo;

    public ArrayList<GamerLevel> getAll() {
        Iterable<GamerLevel> results = gamerLevelRepo.findAll();
        ArrayList<GamerLevel> gamerLevels = new ArrayList<GamerLevel>();

        results.forEach(e -> gamerLevels.add(e));

        return gamerLevels;
    }

    public GamerLevel save(GamerLevel gamerLevel) {
        return gamerLevelRepo.save(gamerLevel);
    }

    public ArrayList<GamerLevel> getGamerLevelByGamerId(long gamerId) {
        Optional<Gamer> gamer = gamerRepo.getGamerById(gamerId);

        if (gamer.isPresent()) {
            return gamerLevelRepo.findByGamerId(gamerId);
        }

        return null;
    }

    public Optional<GamerLevel> getGamerLevelById(long id) {
        return gamerLevelRepo.findById(id);
    }

    public GamerLevel getGamerLevelByGamerAndGameId(long gamerId, long gameId) {
        Optional<GamerLevel> gamerLevel = gamerLevelRepo.findByGamerIdAndGameId(gamerId, gameId);

        if (gamerLevel.isPresent()) {
            return gamerLevel.get();
        }

        return null;
    }

    public ArrayList<GamerLevel> getGamerLevelByLevelId(long levelId) {
        return gamerLevelRepo.findByLevelId(levelId);
    }

    public ArrayList<GamerLevel> findByGameIdAndLevelId(long gameId, long levelId) {
        return gamerLevelRepo.findByGameIdAndLevelId(gameId, levelId);
    }

    public Optional<GamerLevel> findByGamerIdAndGameIdAndLevelId(long gamerId, long gameId, long levelId) {
        return gamerLevelRepo.findByGamerIdAndGameIdAndLevelId(gamerId, gameId, levelId);
    }
}