package com.daw.persistence.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Desayuno;

public interface DesayunoCrudRepository extends ListCrudRepository<Desayuno, Integer>{
	
	List<Desayuno> findByIdEstablecimientoOrderByPuntuacionDesc(Integer idEstablecimiento);
	List<Desayuno> findByIdEstablecimientoOrderByPrecioDesc(Integer idEstablecimiento);
	List<Desayuno> findAllByOrderByPuntuacionDesc();
	List<Desayuno> findDesayunoByIdEstablecimiento(Integer idEstablecimiento);
 
}
