package com.example.demo;

/**
 * Clase que contiene cadenas de texto que son constantes, para que sea más fácil refactorizar y mantener el código
 * @author Cristina Herráez Pérez
 */

public class StaticData 
{
	//Máquina de estados
	public static final String EVENTO_INICIAR = "/start";
	public static final String EVENTO_IR_AL_ESTADO_INICIAL = "/sos";
	public static final String EVENTO_IR_AL_ESTADO_ANTERIOR = "/volver";
	public static final String TEXTO_BOTON_ACEPTAR = "Aceptar";
	public static final String TEXTO_BOTON_RECHAZAR = "Rechazar";
	public static final String EVENTO_MANDAR_PDF = "/pdf";
	public static final String EVENTO_SI = "/si";
	public static final String EVENTO_NO = "/no";
	public static final String EVENTO_OK = "/ok";
	public static final String EVENTO_AYUDA = "/help";
	public static final String EVENTO_MAYOR = "/mayor";
	public static final String EVENTO_MENOR = "/menor";
	
	//Telegram
	public static final int NUMERO_FILAS_TECLADO = 2;
	public static final String URL_FISIOBOT = "http://localhost:8080/longpolling/start";	
	public static final String BOT_TOKEN = "";
	public static final String BOT_USERNAME = "FisioBot";
	public static final String METODO_SENDMESSAGE = "/sendMessage";
	public static final String METODO_SENDDOCUMENT = "/sendDocument";
	public static final String MENSAJE_TERMINOS_Y_CONDICIONES = "\n\n_Todos los ejercicios aquí propuestos están avalados por " +
																"un fisioterapeuta.\n\nAl aceptar los términos y condiciones " +
																"muestras tu conformidad con la utilización de este bot bajo " +
																"tu responsabilidad.\n\nNo obstante, te recomendamos que este " +
																"bot sea una ayuda complementaria y que acudas a un " +
																"fisioterapeuta que realice una valoración 100% personalizada._";
	public static final String MENSAJE_RECIBIR_PDF = "\n\nEscribe /pdf para recibir una serie de ejercicios adecuados para " +
													"tu lesión";
	public static final String DOCUMENTO_AYUDA = "ayuda.pdf";

	//Redis
	public static final String IP_LOCALHOST = "127.0.0.1";
	public static final int REDIS_PORT = 6379;
		
	//Validación del usuario
	public static final String USUARIO = "usuario";
	public static final String HOMBRE = "hombre";
	public static final String MUJER = "mujer";
	public static final String REGEX_FECHAS = "^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
	
	//Identificadores de estado de la máquina de estados 
	public static final int ID_ESTADO_TIENE_HISTORIAL = 127;
	public static final int ID_ESTADO_FECHA_NACIMIENTO = 128;
	public static final int ID_ESTADO_SEXO = 129;
	public static final int ID_ESTADO_EDAD = 130;
}