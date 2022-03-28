package com.milegado.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.milegado.entities.Foto;
import com.milegado.repositories.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	public Foto guardar(MultipartFile archivo) {
		if (archivo != null) {
			try {
				Foto foto = new Foto();
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());

				foto.setContenido(archivo.getBytes());

				return fotoRepository.save(foto);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	public Foto actualizar(String idFoto, MultipartFile archivo) {
		if (idFoto != null) {
			try {
				Foto foto = buscarPorId(idFoto);
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());

				return fotoRepository.save(foto);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	public Foto buscarPorId(String idFoto) throws Exception {
		Optional<Foto> optional = fotoRepository.findById(idFoto);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			throw new Exception("Id de foto no encontrado");
		}
	}

}
