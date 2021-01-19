package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.Gamer;

public interface GamerRepositoryInterface extends CrudRepository<Gamer, Long> {

}