package com.example.demo.statemachine.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.repositorios.EstadoRepository;

/**
 * Clase que representa la lógica que se puede realizar con la entidad Estado.
 * @author Cristina Herráez Pérez
 */

@Service
public class EstadoService implements IEstadoService
{
	@Autowired
	private EstadoRepository estadoRepository; ///< Repositorio de la clase Estado.
	
	/**
	 * Busca todos los estados que haya guardados en MySQL.
	 * @return Lista de todos los estados que existen en la base de datos
	 */
	@Override
	public List<Estado> findAll()
	{
		return (List<Estado>) estadoRepository.findAll();
	}
	
	/**
	 * Busca un estado en MySQL por su id.
	 * @param id Id del estado
	 * @return Objeto de la entidad estado que coincida con el id o un Optional vacío si no existe ninguno
	 */
	@Override
	public Optional<Estado> findById(int id)
	{
		return estadoRepository.findById(id);
	}
	
	/**
	 * Busca cuál es el primer estado de la máquina de estados.
	 * @return El estado inicial
	 */
	@Override
	public Estado findEstadoInicial()
	{
		return estadoRepository.findEstadoInicial();
	}
}
