package com.example.demo.historial;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.mongo.servicios.IUsuarioService;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Usuario;

/**
 * Clase que gestiona el estado en el que se pide al usuario la fecha de nacimiento.
 * @author Cristina Herráez Pérez
 */

@Service
public class FechaNacimientoManageState
{
	@Autowired
	private IUsuarioService usuarioService; ///< Servicio de la clase Usuario que conecta con el repositorio de MongoDB.
	
	/**
	 * Realiza la validación de la fecha introducida por el usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @param fecha Fecha introducida por el usuario
	 * @return true si la fecha es válida
	 */
	public boolean execute(StateMachine<Estado, String> stateMachine, String fecha) 
	{
		boolean seHaGeneradoTransicion = false;
		
		Pattern pattern = Pattern.compile(StaticData.REGEX_FECHAS);
      	Matcher matcher = pattern.matcher(fecha);
      	
      	if (matcher.find()) 
      	{
      		// Actualizamos el usuario en Mongo
      		aniadirFechaAlUsuario(stateMachine, fecha);
      		
      		// Provocamos la transición
      		stateMachine.sendEvent(StaticData.EVENTO_OK);
      		seHaGeneradoTransicion = true;
      	}
      	
      	return seHaGeneradoTransicion;
	}

	/**
	 * Añade la fecha al usuario en MongoDB.
	 * @param stateMachine Máquina de estados del usuario
	 * @param fecha Fecha introducida por el usuario
	 */
	private void aniadirFechaAlUsuario(StateMachine<Estado, String> stateMachine, String fecha) 
	{
		Optional<Usuario> usuario = usuarioService.findById(stateMachine.getId());
		
		if (usuario.isPresent())
		{
			usuario.get().setFechaNacimiento(fecha);
			usuarioService.save(usuario.get());
		}
	}
}
