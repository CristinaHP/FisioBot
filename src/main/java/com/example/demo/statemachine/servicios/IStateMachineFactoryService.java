package com.example.demo.statemachine.servicios;

import org.springframework.statemachine.StateMachine;

import com.example.demo.statemachine.modelo.Estado;

/**
 * Interfaz que representa las operaciones que se van a poder realizar con una máquina de estados.
 * @author Cristina Herráez Pérez
 */

public interface IStateMachineFactoryService 
{
	/**
	 * Obtiene una máquina de estados de la factoría.
	 * @param id ChatId de Telegram del usuario
	 * @return Una máquina de estados
	 */
	public StateMachine<Estado, String> getStateMachine(String id);
	
	/**
	 * Establece un estado concreto desde el que partir en la máquina de estados.
	 * @param stateMachine Máquina de estados del usuario
	 * @param estado Estado que se quiere establecer en la máquina de estados
	 * @return La máquina de estados con el estado establecido
	 */
	public StateMachine<Estado, String> goToState(StateMachine<Estado, String> stateMachine, Estado estado);
	
	/**
	 * Salta el estado de los Términos y condiciones.
	 * @param stateMachine Máquina de estados del usuario
	 * @return La máquina de estados una vez saltados los Términos y condiciones
	 */
	public StateMachine<Estado, String> skipTerminosYCondiciones(StateMachine<Estado, String> stateMachine);
}
