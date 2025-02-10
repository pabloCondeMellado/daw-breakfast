package com.daw.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.daw.persistence.entities.Review;
import com.daw.services.DesayunoServices;
import com.daw.services.ReviewServices;
import com.daw.services.dtos.ReviewDto;

@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewServices reviewServices;
	@Autowired
	private DesayunoServices desayunoServices;
	
	@GetMapping
	public ResponseEntity<List<Review>> listaReview(){
		return ResponseEntity.ok(this.reviewServices.findAll());
	}
	
	@GetMapping("/{idReview}")
	public ResponseEntity<Review> getReviewById(@PathVariable int idReview){
		Optional<Review> desayuno = this.reviewServices.findById(idReview);
		if(desayuno.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(desayuno.get());
	}
	
	@PostMapping
	public ResponseEntity<Review> createReview(@RequestBody Review review){
		return new ResponseEntity<Review>(this.reviewServices.create(review), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idReview}")
	public ResponseEntity<Review> updateReview(@PathVariable int idReview, @RequestBody Review review){
		if(idReview != review.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.reviewServices.deleteReview(idReview)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{idReview}")
	public ResponseEntity<Review> deleteReview(@PathVariable int idReview){
		if(this.reviewServices.deleteReview(idReview)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/puntuacion")
	public ResponseEntity<List<Review>> findReviewsOrderByPuntuacion() {
	    return ResponseEntity.ok(this.reviewServices.findReviewsOrderByPuntuacion());
	}

	@GetMapping("/ordenar/recientes")
	public ResponseEntity<List<Review>> getReviewsOrderByRecientes() {
		return ResponseEntity.ok(reviewServices.findReviewsOrderByFechaRecientes());
	}

	@GetMapping("/ordenar/antiguas")
	public ResponseEntity<List<Review>> getReviewsOrderByAntiguas() {
		return ResponseEntity.ok(reviewServices.findReviewsOrderByFechaAntiguas());
	}

	@GetMapping("/reciente/{idDesayuno}")
	public ResponseEntity<List<ReviewDto>> reviewPorDesayunoMasReciente(@PathVariable int idDesayuno){
		if(this.desayunoServices.existsDesayuno(idDesayuno)) {
			return ResponseEntity.ok(this.reviewServices.getReviewByDesayunoMasRecientes(idDesayuno));
		}
		
		return ResponseEntity.notFound().build();
	}
}
