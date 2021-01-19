package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.GeographyRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.Geography;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class GeographyRepository {

    @Autowired
    private GeographyRepositoryInterface geographyRepo;

    public ArrayList<Geography> getAll() {
        Iterable<Geography> results = geographyRepo.findAll();
        ArrayList<Geography> geographies = new ArrayList<Geography>();

        results.forEach(e -> geographies.add(e));

        return geographies;
    }

    public Geography save(Geography geo) {
        return geographyRepo.save(geo);
    }

    public Optional<Geography> getGeographyById(long id) {
        return geographyRepo.findById(id);
    }
}