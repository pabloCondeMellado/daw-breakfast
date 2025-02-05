package com.daw.persistence.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Desayuno;

public interface DesayunoCrudRepository extends ListCrudRepository<Desayuno, Integer>{

	List<Desayuno> findDesayunoEstablecimientoPuntuacion(Integer idEstablecimiento);
	List<Desayuno> findDesayunoEstablecimientoPrecio(Integer idEstablecimiento);
	List<Desayuno> findDesayunosPorPuntuacion();

}
