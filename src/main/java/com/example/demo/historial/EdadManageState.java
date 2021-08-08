package com.example.demo.historial;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.mongo.servicios.IUsuarioService;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Usuario;

/**
 * Clase que gestiona la edad del usuario para tomar una decisión y mandar un mensaje u otro.
 * @author Cristina Herráez Pérez
 */

@Service
public class EdadManageState 
{
	@Autowired
	private IUsuarioService usuarioService; ///< Servicio de la clase Usuario que conecta con el repositorio de MongoDB.
	
	/**
	 * Realiza la validación de la edad que tiene el usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @return true Siempre, ya que necesitamos que siempre se genere la transición
	 */
	public boolean execute(StateMachine<Estado, String> stateMachine)
	{
		Usuario usuario = usuarioService.findById(stateMachine.getId()).get();
		
		if (LocalDate.now().minusYears(85).isAfter(usuario.getFechaNacimiento()))
		{
			stateMachine.sendEvent(StaticData.EVENTO_MAYOR);
		}
		else
		{
			stateMachine.sendEvent(StaticData.EVENTO_MENOR);
		}
  		
		return true;
	}
}
