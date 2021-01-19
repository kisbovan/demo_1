package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.Level;

public interface LevelRepositoryInterface extends CrudRepository<Level, Long> {

}