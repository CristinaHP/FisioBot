package com.example.demo.telegram.servicios;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

/**
 * Interfaz que representa las operaciones que se van a poder realizar con teclado que se le va a enviar al usuario.
 * @author Cristina Herráez Pérez
 */

public interface ITecladoService 
{
	/**
	 * Genera un teclado personalizado.
	 * @return El teclado personalizado
	 */
	public ReplyKeyboardMarkup generarTecladoPersonalizado();
	
	/**
	 * Genera un objeto que se encarga de eliminar el teclado personalizado en el bot del usuario.
	 * @return El objeto que elimina el teclado personalizado
	 */
	public ReplyKeyboardRemove eliminarTecladoPersonalizado();
}
