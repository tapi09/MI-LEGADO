package com.milegado.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Perfil extends Usuario {

	private String nombre;
	private String apellido;
	protected byte[] foto;
	protected byte[] fotoPortada;
	@OneToOne
	private Conmemoracion conmemoracion;
	/*
	 * @OneToMany List<Conmemoracion> conmemoracion;
	 */

}
