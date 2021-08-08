package com.example.demo.telegram.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.example.demo.StaticData;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Tipo;
import com.example.demo.statemachine.servicios.ITransicionService;

/**
 * Clase que contiene la lógica necesaria para construir un mensaje para el usuario.
 * @author Cristina Herráez Pérez
 */

@Service
public class MensajeService implements IMensajeService 
{
	@Autowired
	private ITransicionService transicionService; ///< Servicio de la clase Transición que conecta con el repositorio de MySQL.
	
	/**
	 * Genera un mensaje de texto para el usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @return El mensaje de texto
	 */
	@Override
	public String generarMensaje(StateMachine<Estado, String> stateMachine) 
	{
		List<String> acciones = new ArrayList<>();
		Estado estadoActual = stateMachine.getState().getId();
		String mensaje = estadoActual.getPregunta();
		
		/*
		 * Si el ESTADO no es el INICIAL, se mandan junto al texto del mensaje aquellos comandos que
		 * representan las posibles respuestas al mensaje, para que el usuario sepa qué opciones tiene
		 * para contestar
		 */
		if (estadoActual.getTipo().getTipo() == Tipo.INICIAL)
		{
			mensaje += StaticData.MENSAJE_TERMINOS_Y_CONDICIONES;
		}
		else if (estadoActual.getTipo().getTipo() == Tipo.INTERMEDIO)
		{
			// Los comandos se ordenan alfabéticamente en orden inverso
			acciones = transicionService.findAccionesByOrigenOrderByAccionDesc(estadoActual);
			
			mensaje += "\n\n";
			
			for (String accion : acciones)
			{
				mensaje += accion + "\n";
			}
		}
		else if (estadoActual.getTipo().getTipo() == Tipo.FINAL)
		{
			mensaje += StaticData.MENSAJE_RECIBIR_PDF;
		}

		return mensaje;
	}

	/**
	 * Genera una ruta a un documento que contiene los ejercicios que se le van a enviar al usuario.
	 * @param stateMachine Máquina de estados del usuario
	 * @return La ruta al documento
	 */
	@Override
	public String generarRutaAlDocumentoConEjercicios(StateMachine<Estado, String> stateMachine) 
	{
		Estado estadoActual = stateMachine.getState().getId();
		String ruta = estadoActual.getPregunta();

		return ruta;
	}

	/**
	 * Genera una ruta a un documento que contiene una guía sencilla sobre cómo utilizar el bot.
	 * @return La ruta al documento
	 */
	@Override
	public String generarRutaAlDocumentoDeAyuda() 
	{
		return StaticData.DOCUMENTO_AYUDA;
	}
}
