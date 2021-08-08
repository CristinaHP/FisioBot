package com.example.demo.telegram.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.example.demo.StaticData;

/**
 * Clase que contiene la lógica necesaria para contruir un teclado para el usuario.
 * @author Cristina Herráez Pérez
 */

@Service
public class TecladoService implements ITecladoService
{
	/**
	 * Genera un teclado personalizado.
	 * @return El teclado personalizado
	 */
	@Override
	public ReplyKeyboardMarkup generarTecladoPersonalizado() 
	{
		ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		List<KeyboardRow> filas = new ArrayList<>();
		
		for (int i = 0; i < StaticData.NUMERO_FILAS_TECLADO; i++)
		{
			KeyboardRow fila = new KeyboardRow();
			KeyboardButton boton = new KeyboardButton();
			
			if (i == 0)
			{
				boton.setText(StaticData.TEXTO_BOTON_ACEPTAR);
			}
			else
			{
				boton.setText(StaticData.TEXTO_BOTON_RECHAZAR);
			}
			
			fila.add(boton);
			filas.add(fila);
		}
		
        keyboard.setKeyboard(filas);
        keyboard.setOneTimeKeyboard(true);
        
        return keyboard;
	}

	/**
	 * Genera un objeto que se encarga de eliminar el teclado personalizado en el bot del usuario.
	 * @return El objeto que elimina el teclado personalizado
	 */
	@Override
	public ReplyKeyboardRemove eliminarTecladoPersonalizado() 
	{
		return new ReplyKeyboardRemove(true);
	}
}
