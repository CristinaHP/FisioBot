package com.example.demo.telegram.bot;

import java.io.File;
import java.util.Iterator;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.demo.StaticData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase que hace de intermediaria entre el bot de Telegram y el controlador.
 * @author Cristina Herráez Pérez
 */

@Component
@Configuration
public class MiLongPollingBot extends TelegramLongPollingBot
{	
	/**
	 * Método principal que realiza el registro del bot en los servidores de Telegram.
	 * @param args Argumentos opcionales
	 */
	public static void main(String[] args)
	{
		try 
		{
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new MiLongPollingBot());
        } 
		catch (TelegramApiException e) 
		{
            e.printStackTrace();
        }
	}

	/**
	 * Devuelve el Username del bot.
	 * @return Username del bot
	 */
	@Override
	public String getBotUsername() 
	{
		return StaticData.BOT_USERNAME;
	}

	/**
	 * Devuelve el token del bot.
	 * @return Token del bot
	 */
	@Override
	public String getBotToken() 
	{
		return StaticData.BOT_TOKEN;
	}

	/**
	 * Recibe los mensajes del usuario del bot y gestiona la respuesta del controlador.
	 * @param update Mensaje que envía el usuario a través de Telegram
	 */
	@Override
	public void onUpdateReceived(Update update) 
	{
		Object contenido = mandarUpdateAlControlador(update);

		if (contenido instanceof SendMessage)
		{
			SendMessage mensaje = (SendMessage) contenido;
			mandarMensaje(mensaje);
		}
		else if (contenido instanceof SendDocument)
		{
			SendDocument documento = (SendDocument) contenido;
			String ruta = documento.getCaption();
			documento.setDocument(new InputFile(new File(ruta)));
			mandarDocumento(documento);
		}
	}

	/**
	 * Envía el mensaje al controlador.
	 * @param update Mensaje que envía el usuario a través de Telegram
	 * @return Objeto que contiene el tipo de mensaje que se va a enviar al usuario
	 */
	private Object mandarUpdateAlControlador(Update update)
	{
		String url = StaticData.URL_FISIOBOT;
		RestTemplate template = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		String response;
		JsonNode raiz;
		Iterator<JsonNode> nodos;
		Class<?> tipoMensaje;
		Object contenido;
		
		try
		{
			response = template.postForObject(url, update, String.class);
			System.out.println("Update mandado al controlador");
			
			raiz = mapper.readTree(response);
			nodos = raiz.elements();
			
			tipoMensaje = mapper.readValue(nodos.next().toString(), Class.class);
			contenido = mapper.readValue(nodos.next().toString(), tipoMensaje);
		}
		catch (HttpClientErrorException | JsonProcessingException e)
		{
			System.out.println("Error mandando update");
			contenido = null;
		}
		
		return contenido;
	}
	
	/**
	 * Ejecuta la acción de mandar un mensaje al Telegram del usuario.
	 * @param mensaje Objeto con el mensaje que se quiere enviar al usuario
	 */
	private void mandarMensaje(SendMessage mensaje) 
	{
		try 
		{
			execute(mensaje);
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Ejecuta la acción de mandar un documento al Telegram del usuario.
	 * @param documento Objeto con el documento que se quiere enviar al usuario
	 */
	private void mandarDocumento(SendDocument documento) 
	{
		try 
		{
			execute(documento);
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}
}
