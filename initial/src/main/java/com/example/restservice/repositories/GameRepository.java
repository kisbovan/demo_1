package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.GameRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.Game;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class GameRepository {

    @Autowired
    private GameRepositoryInterface gameRepo;

    public ArrayList<Game> getAll() {
        Iterable<Game> results = gameRepo.findAll();
        ArrayList<Game> games = new ArrayList<Game>();

        results.forEach(e -> games.add(e));

        return games;
    }

    public Optional<Game> getGameByGameId(long id) {
        return gameRepo.findById(id);
    }

}