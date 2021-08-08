package com.example.demo.historial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Tipo;

/**
 * Clase que gestiona los estados relacionados con el historial del usuario.
 * @author Cristina Herráez Pérez
 */

@Service
public class HistorialService implements IHistorialService
{
	@Autowired
	TieneHistorialManageState tieneHistorial; ///< Clase que gestiona si el usuario tiene ya un historial.
	
	@Autowired
	FechaNacimientoManageState fechaNacimiento; ///< Clase que gestiona la fecha de nacimiento del usuario.
	
	@Autowired
	GeneroManageState genero; ///< Clase que gestiona el género del usuario.
	
	@Autowired
	EdadManageState edad; ///< Clase que gestiona si el usuario es mayor o menor de una determinada edad.
	
	/**
	 * Gestiona los estados relacionados con el historial del usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @param textoIntroducidoPorUsuario Texto introducido por el usuario
	 */
	@Override
	public void gestionaEstado(StateMachine<Estado, String> stateMachine, String textoIntroducidoPorUsuario) 
	{
		boolean seHaGeneradoTransicion = false;
		
		switch (stateMachine.getState().getId().getId())
		{
			case StaticData.ID_ESTADO_TIENE_HISTORIAL:
				seHaGeneradoTransicion = tieneHistorial.execute(stateMachine);
				break;
				
			case StaticData.ID_ESTADO_FECHA_NACIMIENTO:
				seHaGeneradoTransicion = fechaNacimiento.execute(stateMachine, textoIntroducidoPorUsuario);
				break;
				
			case StaticData.ID_ESTADO_SEXO:
				seHaGeneradoTransicion = genero.execute(stateMachine, textoIntroducidoPorUsuario);
				break;
				
			case StaticData.ID_ESTADO_EDAD:
				seHaGeneradoTransicion = edad.execute(stateMachine);
				break;
		}
		
		if (seHaGeneradoTransicion && stateMachine.getState().getId().getTipo().getTipo() == Tipo.CUESTIONARIO)
		{
			gestionaEstado(stateMachine, "");
		}
	}
}
