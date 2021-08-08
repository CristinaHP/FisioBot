package com.example.demo.statemachine.config;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.example.demo.statemachine.modelo.Estado;
import com.example.demo.statemachine.modelo.Tipo;

/**
 * Clase que representa el escuchador de la máquina de estados
 * @author Cristina Herráez Pérez
 */

public class StateMachineListener extends StateMachineListenerAdapter<Estado, String>
{
	/**
	 * Método que se ejecuta cada vez que la máquina de estados cambia de estado. En este caso mostramos información en la 
	 * consola del servidor.
	 * @param from Estado origen
	 * @param to Estado destino
	 */
	@Override
    public void stateChanged(State<Estado, String> from, State<Estado, String> to) 
    {
    	String desde = from == null ? "" : ("Estoy en el estado <" + from.getId().getPregunta() + ">");
       	String hasta = to == null ? "" : ("y voy al estado <" + to.getId().getPregunta() + ">");
   		System.out.println(desde + " " + hasta);
   		
   		if (to.getId().getTipo().getTipo() == Tipo.FINAL)
   		{
   			System.out.println("*************FIN*************");
   		}
    } 
}
