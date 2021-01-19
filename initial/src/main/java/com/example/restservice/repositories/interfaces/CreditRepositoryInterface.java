package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.Credit;

import java.util.ArrayList;

public interface CreditRepositoryInterface extends CrudRepository<Credit, Long> {

    public ArrayList<Credit> findByGamerIdAndGameId(long gamerId, long gameId);
}