package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.GenderRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.Gender;
import java.lang.Iterable;
import java.util.ArrayList;

@Service
public class GenderRepository {

    @Autowired
    private GenderRepositoryInterface genderRepo;

    public ArrayList<Gender> getAll() {
        Iterable<Gender> results = genderRepo.findAll();
        ArrayList<Gender> genders = new ArrayList<Gender>();

        results.forEach(e -> genders.add(e));

        return genders;
    }

}