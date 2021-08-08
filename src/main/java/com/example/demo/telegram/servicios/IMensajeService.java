package com.example.demo.telegram.servicios;

import org.springframework.statemachine.StateMachine;

import com.example.demo.statemachine.modelo.Estado;

/**
 * Interfaz que representa las operaciones que se van a poder realizar con los mensajes que se le van a enviar al usuario.
 * @author Cristina Herráez Pérez
 */

public interface IMensajeService 
{
	/**
	 * Genera un mensaje de texto para el usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @return El mensaje de texto
	 */
	public String generarMensaje(StateMachine<Estado, String> stateMachine);
	
	/**
	 * Genera una ruta a un documento con ejercicios que se le va a enviar al usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @return La ruta al documento
	 */
	public String generarRutaAlDocumentoConEjercicios(StateMachine<Estado, String> stateMachine);
	
	/**
	 * Genera una ruta a un documento de ayuda sobre el bot.
	 * @return La ruta al documento
	 */
	public String generarRutaAlDocumentoDeAyuda();
}
