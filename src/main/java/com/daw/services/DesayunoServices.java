package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.DesayunoCrudRepository;
import com.daw.persistence.entities.Desayuno;


@Service
public class DesayunoServices {
	//añado un comentario
	@Autowired
	private DesayunoCrudRepository desayunoCrudRepository;
	public List<Desayuno> findAll(){
		return this.desayunoCrudRepository.findAll();
	}
	
	public Optional<Desayuno> findById(int idDesayuno){
		return this.desayunoCrudRepository.findById(idDesayuno);
	}
	
	public boolean existsDesayuno(int idDesayuno){
		return this.desayunoCrudRepository.existsById(idDesayuno);
	}
	public Desayuno create(Desayuno desayuno) {
		desayuno.setPuntuacion(0.0);
		return this.desayunoCrudRepository.save(desayuno);
	}
	
	public Desayuno save(Desayuno desayuno) {
		return this.desayunoCrudRepository.save(desayuno);
	}
	
	public boolean deleteDesayuno(int idDesayuno) {
		boolean result = false;
		if(this.desayunoCrudRepository.existsById(idDesayuno)) {
			this.desayunoCrudRepository.deleteById(idDesayuno);
			result = true;
		}
		
		return result;
	}
	
	public List<Desayuno> findDesayunoEstablecimientoPuntuacion(int idEstablecimiento) {
	    return this.desayunoCrudRepository.findByIdEstablecimientoOrderByPuntuacionDesc(idEstablecimiento);
	}

	public List<Desayuno> findDesayunoEstablecimientoPrecio(int idEstablecimiento) {
	    return this.desayunoCrudRepository.findByIdEstablecimientoOrderByPrecioDesc(idEstablecimiento);
	}

	public List<Desayuno> findDesayunosPorPuntuacion() {
		return desayunoCrudRepository.findAllByOrderByPuntuacionDesc();
	}

	public List<Desayuno> findDesayunoPorEstablecimiento(int idEstablecimiento){
		return this.desayunoCrudRepository.findDesayunoByIdEstablecimiento(idEstablecimiento);
	}
	
	public Desayuno updateImage(int idDesayuno,String nuevaImagen) {
		Desayuno desayuno = this.desayunoCrudRepository.findById(idDesayuno).get();
		
		desayuno.setImagen(nuevaImagen);
		desayuno = this.desayunoCrudRepository.save(desayuno);
		
		return desayuno;
	}
	
}
