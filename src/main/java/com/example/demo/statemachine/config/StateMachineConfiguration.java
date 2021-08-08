package com.example.demo.statemachine.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.example.demo.StaticData;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Transicion;
import com.example.demo.statemachine.servicios.IEstadoService;
import com.example.demo.statemachine.servicios.ITransicionService;

/** 
 * Clase para la configuración de la máquina de estados.
 * @author Cristina Herráez Pérez
 */

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<Estado, String>
{
	@Autowired
	private IEstadoService estadoService; ///< Servicio de la clase Estado que conecta con el repositorio de MySQL.
	
	@Autowired
	private ITransicionService transicionService; ///< Servicio de la clase Transicion que conecta con el repositorio de MySQL.
	
	/**
	 * Configura un escuchador para la máquina de estados, así como si se desea que comience de forma automática.
	 * @param config StateMachineConfigurationConfigurer - Configurador de la máquina de estados
	 */
	@Override 
	public void configure(StateMachineConfigurationConfigurer<Estado, String> config) throws Exception 
	{ 
	  config
	   .withConfiguration()
	   .autoStartup(false)
	   .listener(new StateMachineListener()); 
	} 
	
	/**
	 * Configura la máquina indicando cuál va a ser el estado inicial y cuáles serán todos los demás estados.
	 * @param states StateMachineStateConfigurer - Configurador de la máquina de estados
	 */
	@Override
    public void configure(StateMachineStateConfigurer<Estado, String> states) throws Exception 
	{
		Estado estadoInicial = estadoService.findEstadoInicial();
		List<Estado> estadosList = estadoService.findAll();
		Set<Estado> estadosSet = new HashSet<>();
		
		for (Estado e : estadosList)
		{
			estadosSet.add(e);
		}
		
        states
          .withStates()
          .initial(estadoInicial)
          .states(estadosSet);
    }
	
	/**
	 * Configura la máquina indicando cuáles son las transiciones y cómo y cuándo va a pasar de un estado a otro.
	 * @param transitions StateMachineTransitionConfigurer - Configurador de la máquina de estados
	 */
	@Override
    public void configure(StateMachineTransitionConfigurer<Estado, String> transitions) throws Exception 
	{
		List<Transicion> transicionesList = transicionService.findAll();
		Estado estadoInicial = estadoService.findEstadoInicial();
		
		for (Transicion t : transicionesList)
		{
			// Avanzar
			transitions.withExternal()
						.source(t.getOrigen())
						.target(t.getDestino())
						.event(t.getAccion());
			
			// Ir al estado anterior
			transitions.withExternal()
						.source(t.getDestino())
						.target(t.getOrigen())
						.event(StaticData.EVENTO_IR_AL_ESTADO_ANTERIOR);
			
			// Ir al estado inicial
			transitions.withExternal()
						.source(t.getDestino())
						.target(estadoInicial)
						.event(StaticData.EVENTO_IR_AL_ESTADO_INICIAL);
		}
    }
}
