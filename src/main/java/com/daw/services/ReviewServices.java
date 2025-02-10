package com.daw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.ReviewCrudRepository;
import com.daw.persistence.entities.Review;
import com.daw.services.dtos.ReviewDto;
import com.daw.services.mappers.ReviewMapper;

@Service
public class ReviewServices {
	@Autowired
	private ReviewCrudRepository reviewCrudRepository;
	
	public List<Review> findAll(){
		return this.reviewCrudRepository.findAll();
	}
	
	public Optional<Review> findById(int idReview){
		return this.reviewCrudRepository.findById(idReview);
	}
	
	public boolean existsReview(int idReview){
		return this.reviewCrudRepository.existsById(idReview);
	}
	public Review create(Review review) {
		return this.reviewCrudRepository.save(review);
	}
	
	public Review save(Review review) {
		return this.reviewCrudRepository.save(review);
	}
	
	public boolean deleteReview(int idReview) {
		boolean result = false;
		if(this.reviewCrudRepository.existsById(idReview)) {
			this.reviewCrudRepository.deleteById(idReview);
			result = true;
		}
		
		return result;
	}
	
	public List<Review> findReviewsOrderByPuntuacion() {
	    return this.reviewCrudRepository.findAllByOrderByPuntuacionDesc();
	}

	public List<Review> findReviewsOrderByFechaRecientes() {
		return this.reviewCrudRepository.findAllByOrderByFechaDesc();
	}

	public List<Review> findReviewsOrderByFechaAntiguas() {
		return this.reviewCrudRepository.findAllByOrderByFechaAsc();
	}

	public List<ReviewDto> getReviewByDesayunoMasRecientes(int idDesayuno){
		List<ReviewDto> reviewDTO = new ArrayList<ReviewDto>();
		
		List<Review> reviews =this.reviewCrudRepository.findReviewByIdDesayunoOrderByFechaDesc(idDesayuno);
		
		for(Review r : reviews ) {
			reviewDTO.add(ReviewMapper.toDTO(r));
		}
		
		return reviewDTO;
	}


}
