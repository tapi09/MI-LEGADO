package com.milegado.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

	protected String nombre;
	protected String apellido;
	@OneToOne
	protected Foto foto;
	@OneToOne
	protected Foto fotoPortada;
	@OneToMany
	private List<Conmemoracion> conmemoracion;
	

}
