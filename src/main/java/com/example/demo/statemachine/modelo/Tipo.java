package com.example.demo.statemachine.modelo;

import java.io.Serializable;

/**
 * Enumerado que representa los tipos de estado que hay.
 * @author Cristina Herráez Pérez
 */

public enum Tipo implements Serializable
{
	INICIAL,
	INTERMEDIO,
	FINAL,
	CUESTIONARIO,
	PDF;
}
