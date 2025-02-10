package com.daw.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.ReviewCrudRepository;
import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Review;
import com.daw.services.dtos.ReviewDto;
import com.daw.services.mappers.ReviewMapper;

@Service
public class ReviewServices {
	@Autowired
	private ReviewCrudRepository reviewCrudRepository;
	
	@Autowired
	private UsuarioServices  usuarioServices;
	
	@Autowired
	private DesayunoServices desayunoServices;
	
	
	public List<ReviewDto> findAll(){
		List<ReviewDto> reviewDTO = new ArrayList<ReviewDto>();
		
		for(Review r : this.reviewCrudRepository.findAll()){
			reviewDTO.add(ReviewMapper.toDTO(r));
		}
		
		return reviewDTO;
	}
	
	public ReviewDto findById(int idReview){
		return ReviewMapper.toDTO(this.reviewCrudRepository.findById(idReview).get());
	}
	
	public Optional<Review> findByIdEntity(int idReview){
		return this.reviewCrudRepository.findById(idReview);
	}
	
	public boolean existsReview(int idReview){
		return this.reviewCrudRepository.existsById(idReview);
	}
	public ReviewDto create(Review review) {
		Desayuno desayuno = this.desayunoServices.findById(review.getIdDesayuno()).get();
		review.setFecha(LocalDateTime.now());
		review.setPrecio(desayuno.getPrecio());
		review.setImagen(desayuno.getImagen());
		review = this.reviewCrudRepository.save(review);
		
		review.setUsuario(this.usuarioServices.findById(review.getIdUsuario()).get());
		review.setDesayuno(desayuno);
		
		
		
		return ReviewMapper.toDTO(review);
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
	
	public List<ReviewDto> getReviewDeUsuario(int idUsuario){
		List<ReviewDto> reviewDTO = new ArrayList<ReviewDto>();
		
		List<Review> reviews = this.reviewCrudRepository.findByIdUsuario(idUsuario);
		for(Review r : reviews ) {
			reviewDTO.add(ReviewMapper.toDTO(r));
		}
		
		return reviewDTO;
	}
	
	public List<ReviewDto> getReviewDeDesayuno(int idDesayuno){
		List<ReviewDto> reviewDTO = new ArrayList<ReviewDto>();
		
		List<Review> reviews = this.reviewCrudRepository.findByIdDesayuno(idDesayuno);
		for(Review r : reviews ) {
			reviewDTO.add(ReviewMapper.toDTO(r));
		}
		
		return reviewDTO;
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

	public List<ReviewDto> getReviewByDesayunoMejorPuntuadas(int idDesayuno){
		List<ReviewDto> reviewDTO = new ArrayList<ReviewDto>();
		
		List<Review> reviews =this.reviewCrudRepository.findByIdDesayunoOrderByPuntuacionDesc(idDesayuno);
		
		for(Review r : reviews ) {
			reviewDTO.add(ReviewMapper.toDTO(r));
		}
		
		return reviewDTO;
	}

}
