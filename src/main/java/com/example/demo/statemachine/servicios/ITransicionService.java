package com.example.demo.statemachine.servicios;

import java.util.List;

import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Transicion;

/**
 * Interfaz que representa las operaciones CRUD que se van a poder realizar con la entidad Transición.
 * @author Cristina Herráez Pérez
 */

public interface ITransicionService 
{
	/**
	 * Busca todos las transiciones que haya guardadas en MySQL.
	 * @return Lista de todas las transiciones que existen en la base de datos
	 */
	public List<Transicion> findAll();
	
	/**
	 * Devuelve todas las acciones de un estado origen concreto, ordenado alfabéticamente de forma inversa.
	 * @param origen El estado origen de la transición
	 * @return Lista de las acciones
	 */
	public List<String> findAccionesByOrigenOrderByAccionDesc(Estado origen);
	
	/**
	 * Devuelve todas las acciones de un estado origen concreto, ordenado alfabéticamente.
	 * @param origen El estado origen de la transición
	 * @return Lista de las acciones
	 */
	public List<String> findAccionesByOrigenOrderByAccion(Estado origen);
}
