package com.example.demo.statemachine.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.statemachine.modelo.Estado;

/**
 * Interfaz que representa el repositorio de la entidad Estado.
 * @author Cristina Herráez Pérez
 */

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Integer>
{
	/**
	 * Busca cuál es el primer estado de la máquina de estados.
	 * @return El estado inicial
	 */
	@Query("SELECT e FROM Estado e WHERE e.tipo.tipo = com.example.demo.statemachine.modelo.Tipo.INICIAL")
	public Estado findEstadoInicial();
}
