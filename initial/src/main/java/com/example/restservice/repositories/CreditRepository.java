package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.CreditRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.Credit;
import java.lang.Iterable;
import java.util.ArrayList;

@Service
public class CreditRepository {

    @Autowired
    private CreditRepositoryInterface creditRepo;

    public ArrayList<Credit> getAll() {
        Iterable<Credit> results = creditRepo.findAll();
        ArrayList<Credit> allCredit = new ArrayList<Credit>();

        results.forEach(e -> allCredit.add(e));

        return allCredit;
    }

    public Credit save(Credit credit) {
        return creditRepo.save(credit);
    }

    public ArrayList<Credit> findByGamerIdAndGameId(long gamerId, long gameId) {
        return creditRepo.findByGamerIdAndGameId(gamerId, gameId);
    }
}