package com.milegado.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Foto {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;
	
	private String nombre;
	private String mime;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	private byte[] contenido;

}
