package com.example.demo.statemachine.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para envolver la respuesta del controlador, y en la aplicación del bot "desenvolverla" y mandar al usuario 
 * un tipo de mensaje u otro según lo que contenga.
 * @author Cristina Herráez Pérez
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TipoMensajeWrapper 
{
	private Class<?> tipo; ///< El tipo de la clase que envuelve.
	private Object contenido; ///< El contenido que envuelve.
}
