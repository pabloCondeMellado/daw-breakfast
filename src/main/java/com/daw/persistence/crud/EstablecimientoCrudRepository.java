package com.daw.persistence.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Establecimiento;

public interface EstablecimientoCrudRepository extends ListCrudRepository<Establecimiento, Integer> {
    List<Establecimiento> findEstablecimientoByOrderByPuntuacionDesc();
}
