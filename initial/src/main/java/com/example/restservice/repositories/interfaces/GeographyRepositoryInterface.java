package com.example.restservice.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.models.Geography;

public interface GeographyRepositoryInterface extends CrudRepository<Geography, Long> {

}