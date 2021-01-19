package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.LevelRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.Level;
import java.lang.Iterable;
import java.util.ArrayList;

@Service
public class LevelRepository {

    @Autowired
    private LevelRepositoryInterface levelRepo;

    public ArrayList<Level> getAll() {
        Iterable<Level> results = levelRepo.findAll();
        ArrayList<Level> levels = new ArrayList<Level>();

        results.forEach(e -> levels.add(e));

        return levels;
    }

}