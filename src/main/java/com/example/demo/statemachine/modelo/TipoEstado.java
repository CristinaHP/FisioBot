package com.example.demo.statemachine.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase necesaria para mapear los enumerados como una tabla aparte en la base de datos.
 * @author Cristina Herráez Pérez
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tipos")
public class TipoEstado implements Serializable
{
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; ///< Identificador único y PK en la base de datos.
	
	@Enumerated(value = EnumType.STRING)
	private Tipo tipo; ///< El tipo de estado.
}
