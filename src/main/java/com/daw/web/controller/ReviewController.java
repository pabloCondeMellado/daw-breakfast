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
import com.daw.services.UsuarioServices;
import com.daw.services.dtos.ReviewDto;

@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewServices reviewServices;
	@Autowired
	private DesayunoServices desayunoServices;
	
	@Autowired
	private UsuarioServices usuarioServices;
	@GetMapping
	public ResponseEntity<List<ReviewDto>> listaReview(){
		return ResponseEntity.ok(this.reviewServices.findAll());
	}
	
	@GetMapping("/{idReview}")
	public ResponseEntity<ReviewDto> getReviewById(@PathVariable int idReview){
	if(this.reviewServices.existsReview(idReview)) {
		return ResponseEntity.ok(this.reviewServices.findById(idReview));
		}
	return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<ReviewDto> createReview(@RequestBody Review review){
		if(review.getPuntuacion() > 5 || review.getPuntuacion()<0) {
			return ResponseEntity.badRequest().build();
		}
		if(!this.usuarioServices.exitsUsuario(review.getIdUsuario())) {
			return ResponseEntity.notFound().build();
		}
		if(!this.desayunoServices.existsDesayuno(review.getIdDesayuno())) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<ReviewDto>(this.reviewServices.create(review),HttpStatus.CREATED);
	}
	
	@PutMapping("/{idReview}")
	public ResponseEntity<Review> updateReview(@PathVariable int idReview, @RequestBody Review review){
		if(idReview != review.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.reviewServices.existsReview(idReview)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.reviewServices.save(review));
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

	/*@GetMapping("/ordenar/recientes")
	public ResponseEntity<List<Review>> getReviewsOrderByRecientes() {
		return ResponseEntity.ok(reviewServices.findReviewsOrderByFechaRecientes());
	}

	@GetMapping("/ordenar/antiguas")
	public ResponseEntity<List<Review>> getReviewsOrderByAntiguas() {
		return ResponseEntity.ok(reviewServices.findReviewsOrderByFechaAntiguas());
	}*/

	@GetMapping("/ordenar/recientes")
	public ResponseEntity<List<ReviewDto>> getReviewsOrderByRecientes() {
		return ResponseEntity.ok(reviewServices.findReviewsOrderByFechaRecientes());
	}

	@GetMapping("/ordenar/antiguas")
	public ResponseEntity<List<ReviewDto>> getReviewsOrderByAntiguas() {
		return ResponseEntity.ok(reviewServices.findReviewsOrderByFechaAntiguas());
	}

	@GetMapping("/reciente/{idDesayuno}")
	public ResponseEntity<List<ReviewDto>> reviewPorDesayunoMasReciente(@PathVariable int idDesayuno){
		if(this.desayunoServices.existsDesayuno(idDesayuno)) {
			return ResponseEntity.ok(this.reviewServices.getReviewByDesayunoMasRecientes(idDesayuno));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/puntuacion/{idDesayuno}")
	public ResponseEntity<List<ReviewDto>> reviewPorDesayunoMejorPuntuada(@PathVariable int idDesayuno){
		if(this.desayunoServices.existsDesayuno(idDesayuno)) {
			return ResponseEntity.ok(this.reviewServices.getReviewByDesayunoMejorPuntuadas(idDesayuno));
		}
		
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<List<ReviewDto>> reviewDeUsuario(@PathVariable int idUsuario){
		if(this.usuarioServices.exitsUsuario(idUsuario)) {
			return ResponseEntity.ok(this.reviewServices.getReviewDeUsuario(idUsuario));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/desayuno/{idDesayuno}")
	public ResponseEntity<List<ReviewDto>> reviewDeDesayuno(@PathVariable int idDesayuno){
		if(this.desayunoServices.existsDesayuno(idDesayuno)) {
			return ResponseEntity.ok(this.reviewServices.getReviewDeDesayuno(idDesayuno));
		}
		return ResponseEntity.notFound().build();
	}
	
}
