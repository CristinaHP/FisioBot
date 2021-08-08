package com.example.demo.statemachine.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Transicion;

/**
 * Interfaz que representa el repositorio de la entidad Transición.
 * @author Cristina Herráez Pérez
 */

@Repository
public interface TransicionRepository extends CrudRepository<Transicion, Integer>
{
	/**
	 * Devuelve todas las acciones de un estado origen concreto, ordenado alfabéticamente de forma inversa.
	 * @param origen El estado origen de la transición
	 * @return Lista de las acciones
	 */
	@Query("SELECT t.accion FROM Transicion t WHERE t.origen = ?1 ORDER BY t.accion DESC")
	public List<String> findAccionesByOrigenOrderByAccionDesc(Estado origen);
	
	/**
	 * Devuelve todas las acciones de un estado origen concreto, ordenado alfabéticamente.
	 * @param origen El estado origen de la transición
	 * @return Lista de las acciones
	 */
	@Query("SELECT t.accion FROM Transicion t WHERE t.origen = ?1 ORDER BY t.accion")
	public List<String> findAccionesByOrigenOrderByAccion(Estado origen);
}
