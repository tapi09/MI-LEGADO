package com.milegado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milegado.entities.Conmemoracion;
import com.milegado.entities.Perfil;
import com.milegado.services.ConmemoracionService;
import com.milegado.services.PerfilService;
import com.milegado.services.UsuarioService;

@Controller
@RequestMapping("/image")

public class ImageController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ConmemoracionService conmemoracionService;
	
	@Autowired
	private PerfilService perfilService;

	@GetMapping("/usuario/{id}")
	public ResponseEntity<byte[]> foto(@PathVariable String id) {
		try {
			Perfil perfil= perfilService.buscarXId(id);
			if (perfil.getFoto() == null) {
				throw new Exception("El usuario no tiene foto asignada");
			}


			byte[] foto = perfil.getFoto();
			
			

			HttpHeaders headers = new HttpHeaders();
			if (perfil.getFoto().equals("image/jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
			if (perfil.getFoto().equals("image/png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}
			
			

			return new ResponseEntity<>(foto, headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	@GetMapping("/portada/{id}")
	public ResponseEntity<byte[]> fotoPortada(@PathVariable String id) {
		try {
			Perfil perfil= perfilService.buscarXId(id);
			if (perfil.getFotoPortada() == null) {
				throw new Exception("El usuario no tiene foto asignada");
			}


			byte[] fotoPortada = perfil.getFotoPortada();
			
			

			HttpHeaders headers = new HttpHeaders();
			
			if (perfil.getFotoPortada().equals("image/jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
			if (perfil.getFotoPortada().equals("image/png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}
			

			return new ResponseEntity<>(fotoPortada, headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	@GetMapping("/conmemoracionFoto/{id}")
	public ResponseEntity<byte[]> conmemoracionFoto(@PathVariable String id) {
		try {
			Conmemoracion conmemoracion= conmemoracionService.buscarXId(id);
			if (conmemoracion.getFoto() == null) {
				throw new Exception("El usuario no tiene foto asignada");
			}


			byte[] foto = conmemoracion.getFoto();
			
			

			HttpHeaders headers = new HttpHeaders();
			if (conmemoracion.getFoto().equals("image/jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
			if (conmemoracion.getFoto().equals("image/png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}
			
			

			return new ResponseEntity<>(foto, headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
