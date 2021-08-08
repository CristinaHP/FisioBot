package com.example.demo.statemachine.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad Usuario.
 * @author Cristina Herráez Pérez
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection = "usuarios")
public class Usuario implements Serializable
{
	@Id
	@EqualsAndHashCode.Include
	private String id; ///< Id del usuario en MongoDB, que equivale a su chatId de Telegram.
	
	private Sexo sexo; ///< El género del usuario.
	
	private String fechaNacimiento; ///< La fecha de nacimiento del usuario.
	
	/**
	 * Getter personalizado para que convierta la fecha de nacimiento de String a LocalDate.
	 * @return La fecha de nacimiento como LocalDate
	 */
	public LocalDate getFechaNacimiento()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(fechaNacimiento, formatter);
	}
}
