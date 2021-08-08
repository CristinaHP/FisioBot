package com.example.demo.mongo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.statemachine.modelo.Usuario;

/**
 * Interfaz que representa el repositorio de la entidad Usuario.
 * @author Cristina Herráez Pérez
 */

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>
{
}
