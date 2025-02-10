package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.DesayunoCrudRepository;
import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Establecimiento;


@Service
public class DesayunoServices {
	//añado un comentario
	@Autowired
	private DesayunoCrudRepository desayunoCrudRepository;
	
	@Autowired
	private EstablecimientoServices establecimientoServices;
	
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
		 this.desayunoCrudRepository.save(desayuno);
		 ActualizarPuntuacionEstablecimiento(desayuno.getIdEstablecimiento());
		return desayuno;
	}
	
	public boolean deleteDesayuno(int idDesayuno) {
		boolean result = false;
		if(this.desayunoCrudRepository.existsById(idDesayuno)) {
			Desayuno desayuno = this.desayunoCrudRepository.findById(idDesayuno).get();
			this.desayunoCrudRepository.deleteById(idDesayuno);
			result = true;
			ActualizarPuntuacionEstablecimiento(desayuno.getIdEstablecimiento());
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
	
	public void ActualizarPuntuacionEstablecimiento(int idEstablecimiento) {
		if(this.establecimientoServices.existsEstablecimiento(idEstablecimiento)) {
			Establecimiento establecimiento = this.establecimientoServices.findById(idEstablecimiento).get();
			double contador = 0;
			double puntuacion = 0;
			double result =0;
			List<Desayuno> desayunos = this.desayunoCrudRepository.findDesayunoByIdEstablecimiento(idEstablecimiento);
			for(Desayuno d : desayunos) {
				puntuacion += d.getPuntuacion();
				contador++;
			}
			if(contador>0) {
				result = puntuacion / contador;
				establecimiento.setPuntuacion(result);
			}else {
				establecimiento.setPuntuacion(0.0);
			}
			this.establecimientoServices.save(establecimiento);
		}
	}
}
