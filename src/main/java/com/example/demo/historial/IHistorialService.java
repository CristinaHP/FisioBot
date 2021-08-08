package com.example.demo.historial;

import org.springframework.statemachine.StateMachine;

import com.example.demo.statemachine.modelo.Estado;

/**
 * Interfaz para la gestión de los estados relacionados con el historial del usuario.
 * @author Cristina Herráez Pérez
 */

public interface IHistorialService 
{
	/**
	 * Gestiona los estados relacionados con el historial del usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @param textoIntroducidoPorUsuario Texto introducido por el usuario
	 */
	public void gestionaEstado(StateMachine<Estado, String> stateMachine, String textoIntroducidoPorUsuario);
}
