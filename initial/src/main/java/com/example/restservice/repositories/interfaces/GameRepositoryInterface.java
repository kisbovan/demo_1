package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.Game;

public interface GameRepositoryInterface extends CrudRepository<Game, Long> {

}