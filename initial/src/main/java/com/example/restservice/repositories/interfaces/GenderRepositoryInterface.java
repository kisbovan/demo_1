package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.Gender;

public interface GenderRepositoryInterface extends CrudRepository<Gender, Long> {

}