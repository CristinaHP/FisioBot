package com.example.demo.historial;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.mongo.servicios.IUsuarioService;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Usuario;

/**
 * Clase que gestiona el estado en el que se comprueba si el usuario ya tiene un historial.
 * @author Cristina Herráez Pérez
 */

@Service
public class TieneHistorialManageState 
{
	@Autowired
	private IUsuarioService usuarioService; ///< Servicio de la clase Usuario que conecta con el repositorio de MongoDB.
	
	/**
	 * Manda un evento a la máquina de estados según si el usurio ya tiene un historial o no.
	 * @param stateMachine Máquina de estados del usuario
	 * @return true siempre, ya que en cualquier caso necesitamos que se genere la transición al siguiente estado
	 */
	public boolean execute(StateMachine<Estado, String> stateMachine) 
	{
		boolean tieneHistorial = comprobarSiTieneHistorial(stateMachine);
				
		stateMachine.sendEvent(tieneHistorial ? StaticData.EVENTO_SI : StaticData.EVENTO_NO);
		
		return true;
	}

	/**
	 * Realiza la comprobación de si el usuario tiene ya un historial.
	 * @param stateMachine Máquina de estados del usuario
	 * @return true si el usuario tiene un historial completo en MongoDB
	 */
	private boolean comprobarSiTieneHistorial(StateMachine<Estado, String> stateMachine) 
	{
		boolean tieneHistorialCompleto = false;
		
		Optional<Usuario> usuario = usuarioService.findById(stateMachine.getId());
		
		if (usuario.isPresent())
		{
			if (usuario.get().getSexo() != null && usuario.get().getFechaNacimiento() != null)
			{
				tieneHistorialCompleto = true;
			}
		}
		
		return tieneHistorialCompleto;
	}	
}