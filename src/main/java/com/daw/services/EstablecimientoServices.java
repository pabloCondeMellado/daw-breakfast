package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.EstablecimientoCrudRepository;
import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Establecimiento;


@Service
public class EstablecimientoServices {
	@Autowired
	private EstablecimientoCrudRepository establecimientoCrudRepository;
	
	public List<Establecimiento> findAll(){
		return this.establecimientoCrudRepository.findAll();
	}
	
	public Optional<Establecimiento> findById(int idEstablecimiento){
		return this.establecimientoCrudRepository.findById(idEstablecimiento);
	}
	
	public boolean existsEstablecimiento(int idEstablecimiento){
		return this.establecimientoCrudRepository.existsById(idEstablecimiento);
	}
	public Establecimiento create(Establecimiento establecimiento) {
		return this.establecimientoCrudRepository.save(establecimiento);
	}
	
	public Establecimiento save(Establecimiento establecimiento) {
		return this.establecimientoCrudRepository.save(establecimiento);
	}
	
	public boolean deleteEstablecimiento(int idEstablecimiento) {
		boolean result = false;
		if(this.establecimientoCrudRepository.existsById(idEstablecimiento)) {
			this.establecimientoCrudRepository.deleteById(idEstablecimiento);
			result = true;
		}
		
		return result;
	}
	
	public List<Establecimiento> getEstablecimientoPorPuntuacion(){
		return this.establecimientoCrudRepository.findEstablecimientoByOrderByPuntuacionDesc();
	}
	
	public List<Establecimiento> getByPoblacion(String poblacion){
		return this.establecimientoCrudRepository.findByUbicacionContaining(poblacion);
	}


}
