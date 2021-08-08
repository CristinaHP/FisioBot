package com.example.demo.statemachine.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.statemachine.modelo.Estado;

/**
 * Clase que representa la lógica que se puede realizar con una máquina de estados.
 * @author Cristina Herráez Pérez
 */

@Service
public class StateMachineFactoryService implements IStateMachineFactoryService
{
	@Autowired
	private StateMachineFactory<Estado, String> factory; ///< Factoría de máquinas de estados.
	
	@Autowired
	private IEstadoService estadoService; ///< Servicio de la clase Estado que conecta con el repositorio de MySQL.
	
	/**
	 * Obtiene una máquina de estados de la factoría.
	 * @param id ChatId de Telegram del usuario
	 * @return Una máquina de estados
	 */
	@Override
	public StateMachine<Estado, String> getStateMachine(String id) 
	{
		return factory.getStateMachine(id);
	}

	/**
	 * Establece un estado concreto desde el que partir en la máquina de estados.
	 * @param stateMachine Máquina de estados del usuario
	 * @param estado Estado que se quiere establecer en la máquina de estados
	 * @return La máquina de estados con el estado establecido
	 */
	@Override
	public StateMachine<Estado, String> goToState(StateMachine<Estado, String> stateMachine, Estado estado) 
	{
		String id = stateMachine.getId();
		
		stateMachine.getStateMachineAccessor()
					.doWithAllRegions(sm -> sm
					.resetStateMachine(new DefaultStateMachineContext<>(estado, null, null, null, null, id)));
		
		return stateMachine;
	}

	/**
	 * Salta el estado de los Términos y condiciones.
	 * @param stateMachine Máquina de estados del usuario
	 * @return La máquina de estados una vez saltados los Términos y condiciones
	 */
	@Override
	public StateMachine<Estado, String> skipTerminosYCondiciones(StateMachine<Estado, String> stateMachine) 
	{
		String id = stateMachine.getId();
		Estado estado = estadoService.findById(StaticData.ID_ESTADO_TIENE_HISTORIAL).get();
		
		stateMachine.getStateMachineAccessor()
		.doWithAllRegions(sm -> sm
		.resetStateMachine(new DefaultStateMachineContext<>(estado, null, null, null, null, id)));
		
		return stateMachine;
	}
}
