package com.example.demo.historial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.mongo.servicios.IUsuarioService;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Sexo;
import com.example.demo.statemachine.modelo.Usuario;

/**
 * Clase que gestiona el estado en el que se pide al usuario el género.
 * @author Cristina Herráez Pérez
 */

@Service
public class GeneroManageState 
{
	@Autowired
	private IUsuarioService usuarioService; ///< Servicio de la clase Usuario que conecta con el repositorio de MongoDB.
	
	/**
	 * Realiza la validación del género introducido por el usuario y actualiza el usuario en MongoDB.
	 * @param stateMachine Máquina de estados del usuario
	 * @param sexo Género introducido por el usuario
	 * @return true si el género es válido
	 */
	public boolean execute(StateMachine<Estado, String> stateMachine, String sexo)
	{
		boolean seHaGeneradoTransicion = false;

		Usuario usuario = usuarioService.findById(stateMachine.getId()).get();
  		
		if (sexo.equalsIgnoreCase(StaticData.MUJER))
		{
			usuario.setSexo(Sexo.Mujer);
      		stateMachine.sendEvent(StaticData.EVENTO_OK);
      		seHaGeneradoTransicion = true;
		}
		else if (sexo.equalsIgnoreCase(StaticData.HOMBRE))
		{
			usuario.setSexo(Sexo.Hombre);
      		stateMachine.sendEvent(StaticData.EVENTO_OK);
      		seHaGeneradoTransicion = true;
		}
		
		usuarioService.save(usuario);
		
		return seHaGeneradoTransicion;
	}
}
