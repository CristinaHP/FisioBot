package com.example.demo.mongo.servicios;

import java.util.List;
import java.util.Optional;

import com.example.demo.statemachine.modelo.Usuario;

/**
 * Interfaz que representa las operaciones CRUD que se van a poder realizar con la entidad Usuario.
 * @author Cristina Herráez Pérez
 */

public interface IUsuarioService 
{
	/**
	 * Inserta o actualiza un usuario en MongoDB.
	 * @param usuario Objeto de la entidad usuario
	 */
	public void save(Usuario usuario);
	
	/**
	 * Busca un usuario en MongoDB por su id.
	 * @param id Id del usuario, equivalente a su chatId de Telegram
	 * @return Objeto de la entidad usuario que coincida con el id o un Optional vacío si no existe ninguno
	 */
	public Optional<Usuario> findById(String id);
	
	/**
	 * Busca todos los usuarios que haya guardados en MongoDB.
	 * @return Lista de todos los usuarios que existen en la base de datos
	 */
	public List<Usuario> findAll();
}
