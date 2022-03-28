package com.milegado.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Conmemoracion {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private String apellido;
	
	@Temporal(TemporalType.DATE)
	private Date fechaDeNacimiento;
	
	@Temporal(TemporalType.DATE)
	private Date fechaDeDefuncion;
	
	@OneToOne
	private Foto foto;
	
	@OneToOne
	private Foto fotoPortada;
	
	@OneToMany
	private List<Foto> album;
	
	
}
