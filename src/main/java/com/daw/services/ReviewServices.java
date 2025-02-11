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
		
		ActualizarPuntuacionDesayuno(review.getIdDesayuno());
		
		return ReviewMapper.toDTO(review);
	}
	
	public Review save(Review review) {
		Desayuno desayuno = this.desayunoServices.findById(review.getIdDesayuno()).get();
		review.setFecha(LocalDateTime.now());
		review.setPrecio(desayuno.getPrecio());
		review.setImagen(desayuno.getImagen());
		this.reviewCrudRepository.save(review);
		ActualizarPuntuacionDesayuno(review.getIdDesayuno());
		return review;
	}
	
	public boolean deleteReview(int idReview) {
		boolean result = false;
		if(this.reviewCrudRepository.existsById(idReview)) {
			Review review = this.reviewCrudRepository.findById(idReview).get();
			this.reviewCrudRepository.deleteById(idReview);
			result = true;
			ActualizarPuntuacionDesayuno(review.getIdDesayuno());
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
	
	public List<ReviewDto> findReviewsOrderByPuntuacion() {
	    List<ReviewDto> reviewDtos = new ArrayList<>();
	    
	    for (Review r : this.reviewCrudRepository.findAllByOrderByPuntuacionDesc()) {
	        reviewDtos.add(ReviewMapper.toDTO(r));
	    }

	    return reviewDtos;
	}


	
	public List<ReviewDto> findReviewsOrderByFechaRecientes() {
		List<ReviewDto> reviewDtos = new ArrayList<>();

		for (Review r : this.reviewCrudRepository.findAllByOrderByFechaDesc()){
			reviewDtos.add(ReviewMapper.toDTO(r));
		}

		return reviewDtos;
	}

	/*public List<Review> findReviewsOrderByFechaRecientes() {
	return this.reviewCrudRepository.findAllByOrderByFechaDesc();
}

public List<Review> findReviewsOrderByFechaAntiguas() {
	return this.reviewCrudRepository.findAllByOrderByFechaAsc();
}*/

	public List<ReviewDto> findReviewsOrderByFechaAntiguas() {
		List<ReviewDto> reviewDtos = new ArrayList<>();

		for (Review r : this.reviewCrudRepository.findAllByOrderByFechaAsc()){
			reviewDtos.add(ReviewMapper.toDTO(r));
		}

		return reviewDtos;
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
	public void ActualizarPuntuacionDesayuno(int idDesayuno) {
		if(this.desayunoServices.existsDesayuno(idDesayuno)) {
			Desayuno desayuno = this.desayunoServices.findById(idDesayuno).get();
			double contador = 0;
			double puntuacion = 0;
			double result =0;
			List<Review> reviews = this.reviewCrudRepository.findByIdDesayuno(idDesayuno);
			for(Review r : reviews) {
				if(r.getPuntuacion()>0.0) {
					puntuacion += r.getPuntuacion();
					contador++;
				}
				
			}
			if(contador>0) {
				 result = puntuacion / contador;
				 desayuno.setPuntuacion(result);
			}else {
				desayuno.setPuntuacion(0.0);
				
			}
			this.desayunoServices.save(desayuno);
			this.desayunoServices.ActualizarPuntuacionEstablecimiento(desayuno.getIdEstablecimiento());	
		}
	}
}
