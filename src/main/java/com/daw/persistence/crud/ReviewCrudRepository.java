package com.daw.persistence.crud;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Review;

public interface ReviewCrudRepository extends ListCrudRepository<Review, Integer>{

}
