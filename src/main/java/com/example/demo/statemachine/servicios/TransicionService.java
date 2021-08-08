package com.example.demo.statemachine.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Transicion;
import com.example.demo.statemachine.repositorios.TransicionRepository;

/**
 * Clase que representa la lógica que se puede realizar con la entidad Transición.
 * @author Cristina Herráez Pérez
 */

@Service
public class TransicionService implements ITransicionService
{
	@Autowired
	private TransicionRepository transicionRepository; ///< Repositorio de la clase Transición.
	
	/**
	 * Busca todos las transiciones que haya guardadas en MySQL.
	 * @return Lista de todas las transiciones que existen en la base de datos
	 */
	public List<Transicion> findAll()
	{
		return (List<Transicion>) transicionRepository.findAll();
	}
	
	/**
	 * Devuelve todas las acciones de un estado origen concreto, ordenado alfabéticamente de forma inversa.
	 * @param origen El estado origen de la transición
	 * @return Lista de las acciones
	 */
	@Override
	public List<String> findAccionesByOrigenOrderByAccionDesc(Estado origen) 
	{
		return transicionRepository.findAccionesByOrigenOrderByAccionDesc(origen);
	}

	/**
	 * Devuelve todas las acciones de un estado origen concreto, ordenado alfabéticamente.
	 * @param origen El estado origen de la transición
	 * @return Lista de las acciones
	 */
	@Override
	public List<String> findAccionesByOrigenOrderByAccion(Estado origen) 
	{
		return transicionRepository.findAccionesByOrigenOrderByAccion(origen);
	}
}
