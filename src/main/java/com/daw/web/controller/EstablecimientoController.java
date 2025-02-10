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

import com.daw.persistence.entities.Establecimiento;
import com.daw.services.EstablecimientoServices;

@RestController
@RequestMapping("/establecimiento")
public class EstablecimientoController {
	@Autowired
	private EstablecimientoServices establecimientoServices;
	
	@GetMapping
	public ResponseEntity<List<Establecimiento>> listaEstablecimiento(){
		return ResponseEntity.ok(this.establecimientoServices.findAll());
	}
	
	@GetMapping("/{idEstablecimiento}")
	public ResponseEntity<Establecimiento> getEstablecimientoById(@PathVariable int idEstablecimiento){
		Optional<Establecimiento> usuario = this.establecimientoServices.findById(idEstablecimiento);
		if(usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(usuario.get());
	}
	
	@PostMapping
	public ResponseEntity<Establecimiento> createEstablecimiento(@RequestBody Establecimiento establecimiento){
		return new ResponseEntity<Establecimiento>(this.establecimientoServices.create(establecimiento), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idEstablecimiento}")
	public ResponseEntity<Establecimiento> updateEstablecimiento(@PathVariable int idEstablecimiento, @RequestBody Establecimiento establecimiento){
		if(idEstablecimiento != establecimiento.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.establecimientoServices.existsEstablecimiento(idEstablecimiento)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Establecimiento> deleteEstablecimiento(@PathVariable int idEstablecimiento){
		if(this.establecimientoServices.deleteEstablecimiento(idEstablecimiento)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/puntuacion")
	public ResponseEntity<List<Establecimiento>> orderEstablecimientoPorPuntuacion(){
		return ResponseEntity.ok(this.establecimientoServices.getEstablecimientoPorPuntuacion());
	}

}
