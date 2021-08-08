package com.example.demo.statemachine.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad Transición.
 * @author Cristina Herráez Pérez
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "transiciones")
public class Transicion implements Serializable
{
	@EqualsAndHashCode.Include
	@Id
	private int id; ///< Identificador único y PK en la base de datos.
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Estado origen; ///< Estado que representa el origen en esta transición.
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Estado destino; ///< Estado que representa el destino en esta transición.
	
	private String accion; ///< Comando que marca el paso del estado origen al estado destino.
}
