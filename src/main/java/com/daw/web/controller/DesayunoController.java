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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Desayuno;
import com.daw.services.DesayunoServices;
import com.daw.services.EstablecimientoServices;

@RestController
@RequestMapping("/desayuno")
public class DesayunoController {
	@Autowired
	private DesayunoServices desayunoServices;
	@Autowired
	private EstablecimientoServices establecimientoServices;
	
	@GetMapping
	public ResponseEntity<List<Desayuno>> listaDesayuno(){
		return ResponseEntity.ok(this.desayunoServices.findAll());
	}
	
	@GetMapping("/{idDesayuno}")
	public ResponseEntity<Desayuno> getDesayunoById(@PathVariable int idDesayuno){
		Optional<Desayuno> desayuno = this.desayunoServices.findById(idDesayuno);
		if(desayuno.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(desayuno.get());
	}
	
	@PostMapping
	public ResponseEntity<Desayuno> createDesayuno(@RequestBody Desayuno desayuno){
		return new ResponseEntity<Desayuno>(this.desayunoServices.create(desayuno), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idDesayuno}")
	public ResponseEntity<Desayuno> updateDesayuno(@PathVariable int idDesayuno, @RequestBody Desayuno desayuno){
		if(idDesayuno != desayuno.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.desayunoServices.existsDesayuno(idDesayuno)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.desayunoServices.save(desayuno));
	}
	
	@DeleteMapping("/{idDesayuno}")
	public ResponseEntity<Desayuno> deleteDesayuno(@PathVariable int idDesayuno){
		if(this.desayunoServices.deleteDesayuno(idDesayuno)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/establecimiento/{idEstablecimiento}/puntuacion")
	public ResponseEntity<List<Desayuno>> findDesayunoEstablecimientoPuntuacion(@PathVariable int idEstablecimiento) {
	    return ResponseEntity.ok(this.desayunoServices.findDesayunoEstablecimientoPuntuacion(idEstablecimiento));
	}

	@GetMapping("/establecimiento/{idEstablecimiento}/precio")
	public ResponseEntity<List<Desayuno>> findDesayunoEstablecimientoPrecio(@PathVariable int idEstablecimiento) {
	    return ResponseEntity.ok(this.desayunoServices.findDesayunoEstablecimientoPrecio(idEstablecimiento));
	}

	@GetMapping("/ordenar/puntuacion")
	public ResponseEntity<List<Desayuno>> findDesayunosPorPuntuacion() {
		return ResponseEntity.ok(desayunoServices.findDesayunosPorPuntuacion());
	}

	@GetMapping("/establecimiento")
	public ResponseEntity<List<Desayuno>> findDesayunoPorEstablecimiento(@RequestParam int idEstablecimiento){
		if(this.establecimientoServices.existsEstablecimiento(idEstablecimiento)) {
			return ResponseEntity.ok(this.desayunoServices.findDesayunoPorEstablecimiento(idEstablecimiento));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{idDesayuno}/imagen")
	public ResponseEntity<Desayuno> updateImage(@PathVariable int idDesayuno, @RequestParam String nuevaImagen){
		if(this.desayunoServices.existsDesayuno(idDesayuno)) {
			return ResponseEntity.ok(this.desayunoServices.updateImage(idDesayuno, nuevaImagen));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}