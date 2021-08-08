package com.example.demo.statemachine.modelo;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad Estado.
 * @author Cristina Herráez Pérez
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "estados")
public class Estado implements Serializable
{
	@Id
	private int id; ///< Identificador único y PK en la base de datos.
	
	private UUID uuid; ///< Identificador único y clave candidata en la base de datos.
	
	private String pregunta; ///< Pregunta o texto que corresponde mandar al usuario cuando está en este estado.
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TipoEstado tipo; ///< El tipo de estado.
	
	
	@Override
	public boolean equals(Object obj) 
	{
		return this.getUuid().equals(((Estado) obj).getUuid());
	}
	
	@Override
	public int hashCode() 
	{
		return this.getUuid().hashCode();
	}
}
