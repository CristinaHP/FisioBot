package com.example.demo.statemachine.servicios;

import java.util.List;
import java.util.Optional;

import com.example.demo.statemachine.modelo.Estado;

/**
 * Interfaz que representa las operaciones CRUD que se van a poder realizar con la entidad Estado.
 * @author Cristina Herráez Pérez
 */

public interface IEstadoService
{
	/**
	 * Busca todos los estados que haya guardados en MySQL.
	 * @return Lista de todos los estados que existen en la base de datos
	 */
	public List<Estado> findAll();
	
	/**
	 * Busca un estado en MySQL por su id.
	 * @param id Id del estado
	 * @return Objeto de la entidad estado que coincida con el id o un Optional vacío si no existe ninguno
	 */
	public Optional<Estado> findById(int id);
	
	/**
	 * Busca cuál es el primer estado de la máquina de estados.
	 * @return El estado inicial
	 */
	public Estado findEstadoInicial();
}
