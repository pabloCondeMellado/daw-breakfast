package com.daw.services.dtos;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
	private Integer id;
	private String Desayuno;
	private String usuario;
	private LocalDateTime fecha;
	private Double precio;
	private String imagen;
	private Integer puntuacion;
	private String comentario;
	
}
