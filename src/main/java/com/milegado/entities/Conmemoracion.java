package com.milegado.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	String id;
	String nombre;
	String apellido;
	
	@Temporal(TemporalType.DATE)
	private Date fechaDeNacimiento;
	
	@Temporal(TemporalType.DATE)
	private Date fechaDeDefuncion;
	
	private byte[] foto;
	
	private byte[] fotoPortada;
	
	
}
