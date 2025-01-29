package com.daw.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="review")
@Getter
@Setter
@NoArgsConstructor
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="id_usuario")
	private Integer idUsuario;
	@Column(name="id_desayuno")
	private Integer idDesayuno;
	@Column(columnDefinition = "DATETIME")
	private LocalDateTime fecha;
	@Column(columnDefinition = "DECIMAL(5,2)")
	private Double precio;
	@Column(columnDefinition = "VARCHAR(255)")
	private String imagen;
	
	private Integer puntuacion;
	
	private String comentarios;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", referencedColumnName = "id", nullable = false, updatable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_desayuno", referencedColumnName = "id", nullable = false, updatable = false)
	private Desayuno desayuno;
}
