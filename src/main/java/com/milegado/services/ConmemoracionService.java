package com.milegado.services;



import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.milegado.entities.Conmemoracion;
import com.milegado.entities.Perfil;
import com.milegado.exceptions.MyException;
import com.milegado.repositories.ConmemoracionRepository;

@Service
public class ConmemoracionService {
	
	@Autowired
	private ConmemoracionRepository conmemoracionRepository;
	@Autowired
	private PerfilService perfilService;
	
	@Transactional
	public void crearConmemoracion(String nombre, String apellido, Date fechaNac, Date fechaDefun, MultipartFile foto, MultipartFile fotoPortada, String id) throws Exception {
		Conmemoracion conmemoracion = new Conmemoracion();
		conmemoracion.setNombre(nombre);
		conmemoracion.setApellido(apellido);
		conmemoracion.setFechaDeNacimiento(fechaNac);
		conmemoracion.setFechaDeDefuncion(fechaDefun);
		conmemoracion.setFoto(foto.getBytes());
		conmemoracion.setFotoPortada(fotoPortada.getBytes());
		Perfil perfil = perfilService.buscarXId(id);
		if (perfil.getConmemoracion() == null) {
		perfilService.guardarConmemoracion(conmemoracionRepository.save(conmemoracion), id); 
		}else {
			Conmemoracion conmemoracion1 = buscarXId(perfil.getConmemoracion().getId());
			conmemoracion1.setNombre(nombre);
			conmemoracion1.setApellido(apellido);
			conmemoracion1.setFechaDeNacimiento(fechaNac);
			conmemoracion1.setFechaDeDefuncion(fechaDefun);
			conmemoracion1.setFoto(foto.getBytes());
			conmemoracion1.setFotoPortada(fotoPortada.getBytes());
			conmemoracionRepository.save(conmemoracion1);
			
		}
		
	}

	public Conmemoracion buscarXId(String id) throws MyException {
		Optional<Conmemoracion> optional= conmemoracionRepository.findById(id);
		if(optional != null) {
			return optional.get();
		}else {
			throw new MyException("idConmemoracion no encontrado");
		}
		
	}
	
}
