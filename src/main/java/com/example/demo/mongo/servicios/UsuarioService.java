package com.example.demo.mongo.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mongo.repositorios.UsuarioRepository;
import com.example.demo.statemachine.modelo.Usuario;

/**
 * Clase que implementa las operaciones CRUD que se van a poder realizar con la entidad Usuario.
 * @author Cristina Herráez Pérez
 */

@Service
public class UsuarioService implements IUsuarioService
{
	@Autowired
	private UsuarioRepository usuarioRepository; ///< Repositorio de la clase Usuario.
	
	/**
	 * Inserta o actualiza un usuario en MongoDB.
	 * @param usuario Objeto de la entidad usuario
	 */
	@Override
	public void save(Usuario usuario) 
	{
		usuarioRepository.save(usuario);
	}

	/**
	 * Busca un usuario en MongoDB por su id.
	 * @param id Id del usuario, equivalente a su chatId de Telegram
	 * @return Objeto de la entidad usuario que coincida con el id o un Optional vacío si no existe ninguno
	 */
	@Override
	public Optional<Usuario> findById(String id) 
	{
		return usuarioRepository.findById(id);
	}

	/**
	 * Busca todos los usuario que haya guardados en MongoDB.
	 * @return Lista de todos los usuarios que existen en la base de datos
	 */
	@Override
	public List<Usuario> findAll() 
	{
		return usuarioRepository.findAll();
	}
}
