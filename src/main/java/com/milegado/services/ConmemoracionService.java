package com.milegado.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.milegado.entities.Conmemoracion;
import com.milegado.entities.Foto;
import com.milegado.entities.Perfil;
import com.milegado.exceptions.MyException;
import com.milegado.repositories.ConmemoracionRepository;

@Service
public class ConmemoracionService {

	@Autowired
	private ConmemoracionRepository conmemoracionRepository;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FotoService fotoService;

	@Transactional
	public void crearConmemoracion(String nombre, String apellido, Date fechaNac, Date fechaDefun, MultipartFile foto1,
			MultipartFile fotoPortada, Authentication usuario) throws Exception {

		Perfil perfil = perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId());
		Conmemoracion conmemoracion = new Conmemoracion();
		conmemoracion.setNombre(nombre);
		conmemoracion.setApellido(apellido);
		conmemoracion.setFechaDeNacimiento(fechaNac);
		conmemoracion.setFechaDeDefuncion(fechaDefun);
		conmemoracion.setFoto(fotoService.guardar(foto1));
		conmemoracion.setFotoPortada(fotoService.guardar(fotoPortada));

		perfilService.guardarConmemoracion(conmemoracionRepository.save(conmemoracion), perfil);
	}

	public Conmemoracion buscarXId(String id) throws MyException {
		Optional<Conmemoracion> optional = conmemoracionRepository.findById(id);
		if (optional != null) {
			return optional.get();
		} else {
			throw new MyException("idConmemoracion no encontrado");
		}
	}
	
	public List<Conmemoracion> listar(Authentication usuario) throws MyException, Exception {
		return conmemoracionRepository.findAll();
	}
	
	public String ultimaConmemoracion(String id)throws MyException, Exception{
		
		List <Conmemoracion> conmemora= conmemoracionRepository.buscarUltima(id);
		
		
		return conmemora.get(conmemora.size()-1).getId();
	}

	public void agregarFoto(MultipartFile foto, Authentication usuario, String idConmemoracion) throws Exception {

		Perfil perfil = perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId());
		List<Conmemoracion> conmemoraciones = perfil.getConmemoracion();
		int count = 0;
		for (Conmemoracion conmemoracion : conmemoraciones) {
			if (conmemoracion.getId().equals(idConmemoracion)) {
				List<Foto> album = conmemoracion.getAlbum();
				album.add(fotoService.guardar(foto));
				conmemoracion.setAlbum(album);
				conmemoracionRepository.save(conmemoracion);
				count=1;
			}
			
		}
		if (count == 0) {
			throw new Exception("no esta autorizado, este memorial no le pertenece");
		}
	}

	public List<Conmemoracion> listarXId(Authentication usuario) throws Exception {
		Perfil perfil = perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId());
		return perfil.getConmemoracion();
	}

	@Transactional
	public void guardarFoto(String id, MultipartFile foto) throws Exception {
		Conmemoracion conmemoracion = buscarXId(id);
		if (foto.getSize() == 0) {
			throw new MyException("primero debe cargar la foto y despues guardar");
		}
		conmemoracion.setFoto(fotoService.guardar(foto));
		conmemoracionRepository.save(conmemoracion);
	}

}
