package com.daw.persistence.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Review;

public interface ReviewCrudRepository extends ListCrudRepository<Review, Integer>{

	List<Review> findAllByOrderByPuntuacionDesc();

}
