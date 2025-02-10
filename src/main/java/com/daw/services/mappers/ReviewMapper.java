package com.daw.services.mappers;

import com.daw.persistence.entities.Review;
import com.daw.services.dtos.ReviewDto;

public class ReviewMapper {
	public static ReviewDto toDTO(Review review) {
		ReviewDto dto = new ReviewDto();
		
		dto.setId(review.getId());
		dto.setDesayuno(review.getDesayuno().getNombre());
		dto.setUsuario(review.getUsuario().getUsername());
		dto.setFecha(review.getFecha());
		dto.setPrecio(review.getDesayuno().getPrecio());
		dto.setImagen(review.getDesayuno().getImagen());
		
		dto.setPuntuacion(review.getPuntuacion());
		dto.setComentario(review.getComentarios());	

		return dto;
		
	}

}
