package com.example.demo.statemachine.controladores;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.demo.mongo.servicios.IUsuarioService;
import com.example.demo.redis.repositorios.EstadoRedisRepository;
import com.example.demo.StaticData;
import com.example.demo.historial.IHistorialService;
import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Tipo;
import com.example.demo.statemachine.modelo.TipoMensajeWrapper;
import com.example.demo.statemachine.modelo.Usuario;
import com.example.demo.statemachine.servicios.IEstadoService;
import com.example.demo.statemachine.servicios.IStateMachineFactoryService;
import com.example.demo.telegram.servicios.IMensajeService;
import com.example.demo.telegram.servicios.ITecladoService;

/**
 * Clase que representa el controlador REST al que irán todos los mensajes que mande el usuario.
 * @author Cristina Herráez Pérez
 */

@RestController
@RequestMapping(path = "/longpolling")
public class LongPollingController
{
	@Autowired
	private ITecladoService tecladoService; ///< Servicio que modifica el teclado que se manda al usuario.
	
	@Autowired 
	private IMensajeService mensajeService; ///< Servicio que gestiona los mensajes que se le envían al usuario.
	
	@Autowired
	private EstadoRedisRepository redisEstadoService; ///< Repositorio con las operaciones CRUD que realizamos con Redis.
	
	@Autowired
	private IStateMachineFactoryService factoryService; ///< Servicio que gestiona las máquinas de estado.
	
	@Autowired
	private IHistorialService historialService; ///< Servicio que gestiona los estados de la máquina de estados relacionados con el historial del usuario.
	
	@Autowired
	private IUsuarioService usuarioService; ///< Servicio de la clase Usuario que conecta con el repositorio de MongoDB.

	@Autowired
	private IEstadoService estadoService;
	
	/**
	 * Recibe el mensaje del usuario y le responde con otro mensaje o con un documento PDF.
	 * @param update Mensaje que envía el usuario a través de Telegram
	 * @return ResponseEntity que contiene un mensaje de texto o un documento PDF para mandar al usuario de Telegram
	 */
	@PostMapping(path = "/start")
	public ResponseEntity<?> start(@RequestBody Update update) 
	{
		Object contenidoMensaje = new Object();
		String chatId = String.valueOf(update.getMessage().getChatId());
		String comando = update.getMessage().getText();
		
		// Cogemos el ESTADO de Redis
		Estado estadoRedis = redisEstadoService.findById(chatId);
		Estado estadoInicial = estadoService.findEstadoInicial(); //

		// Obtenemos una nueva máquina de estados a la que asociamos como identificador el chatId
		StateMachine<Estado, String> stateMachine = factoryService.getStateMachine(chatId);
		
		// Comprobamos si existe el USUARIO en Mongo
		boolean existeUsuario = existeUsuario(chatId);
		
		if (estadoRedis != null)
		{
			// Reseteamos la máquina de estados al ESTADO en el que se quedó
			resetearStateMachine(estadoRedis, stateMachine);
		}
		
		// Si el comando no es de PDF ni de ayuda, mandamos un mensaje de texto
		if (!comando.equals(StaticData.EVENTO_MANDAR_PDF) && !comando.equals(StaticData.EVENTO_AYUDA))
		{
			contenidoMensaje = new SendMessage();
			
			// Si el comando es /start
			if (comando.equals(StaticData.EVENTO_INICIAR) && !existeUsuario)
			{	 
				estadoRedis = estadoInicial; //
				factoryService.goToState(stateMachine, estadoRedis); //
				
				stateMachine.start();
				// Si no existe el chatId en Redis se setea el Keyboard especial
		        ((SendMessage) contenidoMensaje).setReplyMarkup(tecladoService.generarTecladoPersonalizado());
			}
			else
			{
				if (!comando.startsWith("/") && comando.equals(StaticData.TEXTO_BOTON_ACEPTAR))
				{
					// Se crea un USUARIO para Mongo
			        generarUsuario(stateMachine);
				}
				
				((SendMessage) contenidoMensaje).setReplyMarkup(tecladoService.eliminarTecladoPersonalizado());
			}
			
			stateMachine.sendEvent(comando);	
			
			// Si el comando es /sos
			gestionarComandoSos(comando, stateMachine, existeUsuario);
			
			// Si el ESTADO es de tipo cuestionario
			gestionarCuestionario(comando, stateMachine);		
			
			((SendMessage) contenidoMensaje).setChatId(chatId);
			((SendMessage) contenidoMensaje).setText(mensajeService.generarMensaje(stateMachine));
			((SendMessage) contenidoMensaje).setParseMode(ParseMode.MARKDOWN);
		}
		else
		{
			contenidoMensaje = new SendDocument();
			String archivo = "";
			
			if (comando.equals(StaticData.EVENTO_MANDAR_PDF))
			{
				stateMachine.sendEvent(comando);
				// Si el ESTADO es de tipo cuestionario
				gestionarCuestionario(comando, stateMachine);	
				archivo = mensajeService.generarRutaAlDocumentoConEjercicios(stateMachine);
			} 
			else if (comando.equals(StaticData.EVENTO_AYUDA)) //BORRAR
			{
				archivo = mensajeService.generarRutaAlDocumentoDeAyuda();
			}
			
			((SendDocument) contenidoMensaje).setChatId(chatId);
			((SendDocument) contenidoMensaje).setDocument(new InputFile(new File(archivo)));
			((SendDocument) contenidoMensaje).setCaption(archivo);
		}

		guardarEstadoEnRedis(chatId, stateMachine);
		
		TipoMensajeWrapper wrapper = new TipoMensajeWrapper(contenidoMensaje.getClass(), contenidoMensaje);
		ResponseEntity<?> response = new ResponseEntity<>(wrapper, HttpStatus.OK);
		
		return response;
	}

	/**
	 * Llama al servicio que se encarga de gestionar los estados de la máquina de estados sobre el historial del usuario.
	 * @param comando Texto introducido por el usuario
	 * @param stateMachine Máquina de estados del usuario
	 */
	private void gestionarCuestionario(String comando, StateMachine<Estado, String> stateMachine) 
	{
		if (stateMachine.getState().getId().getTipo().getTipo() == Tipo.CUESTIONARIO)
		{
			historialService.gestionaEstado(stateMachine, comando);
		}
	}

	/**
	 * Gestiona el comportamiento que se produce cuando el usuario reinicia la máquina de estados.
	 * @param comando Texto introducido por el usuario
	 * @param stateMachine Máquina de estados del usuario
	 * @param existeUsuario Si existe ya el historial del usuario en MongoDB
	 */
	private void gestionarComandoSos(String comando, StateMachine<Estado, String> stateMachine, boolean existeUsuario) 
	{
		if (comando.equals(StaticData.EVENTO_IR_AL_ESTADO_INICIAL))
		{
			if (existeUsuario)
			{
				saltarTerminosYCondiciones(stateMachine);
			}
		}
	}

	/**
	 * Salat el estado sobre los Términos y condiciones del bot.
	 * @param stateMachine Máquina de estados del usuario
	 */
	private void saltarTerminosYCondiciones(StateMachine<Estado, String> stateMachine) 
	{
		factoryService.skipTerminosYCondiciones(stateMachine);
		stateMachine.start();
	}

	/**
	 * Resetea la máquina de estados a un estado concreto.
	 * @param estadoRedis Estado al que se quiere resetear la máquina de estados
	 * @param stateMachine Máquina de estados del usuario
	 */
	private void resetearStateMachine(Estado estadoRedis, StateMachine<Estado, String> stateMachine) 
	{
		factoryService.goToState(stateMachine, estadoRedis);
		stateMachine.start();
	}

	/**
	 * Crea un usuario nuevo en MongoDB.
	 * @param stateMachine Máquina de estados del usuario
	 */
	private void generarUsuario(StateMachine<Estado, String> stateMachine) 
	{
		if (!existeUsuario(stateMachine.getId()))
		{
			Optional<Usuario> usuario = Optional.of(new Usuario());
			usuario.get().setId(stateMachine.getId());
			usuarioService.save(usuario.get());
		}
	}
	
	/**
	 * Comprueba si ya existe un usuario en MongoDB.
	 * @param chatId ChatId de Telegram del usuario
	 * @return true si el historial del usuario ya existe en MongoDB
	 */
	private boolean existeUsuario(String chatId)
	{
		return usuarioService.findById(chatId).isPresent();
	}
	
	/**
	 * Guarda en Redis el estado de la máquina de estados en el que se encuentra el usuario.
	 * @param chatId ChatId de Telegram del usuario
	 * @param stateMachine Máquina de estados del usuario
	 */
	private void guardarEstadoEnRedis(String chatId, StateMachine<Estado, String> stateMachine) 
	{
		redisEstadoService.save(stateMachine.getState().getId(), chatId);
	}
}
