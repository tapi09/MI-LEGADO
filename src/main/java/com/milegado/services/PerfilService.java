package com.milegado.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.milegado.entities.Conmemoracion;
import com.milegado.entities.Perfil;
import com.milegado.enums.Role;
import com.milegado.exceptions.MyException;
import com.milegado.repositories.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository repository;
	@Autowired
	private UsuarioService usuarioService;

	@Transactional
	public void save(String nombre, String apellido, String username, String password, String password1)
			throws Exception {

		// mi pregunta es si este puede ser el error, al crear un nuevo perfil le genera
		// un id distinto?
		// y por mas que lo setee despues no se modifica?
		Perfil perfil = new Perfil();

		validar(nombre, apellido, username, password, password1);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		perfil.setNombre(nombre);
		perfil.setApellido(apellido);
		perfil.setUsername(username);
		perfil.setContraseña(encoder.encode(password));
		perfil.setRol(Role.USUARIO);

		repository.save(perfil);

	}

	public void validar(String nombre, String apellido, String username, String password, String password1)
			throws Exception {
		if (nombre.isEmpty() || nombre == null) {
			throw new MyException("El nombre no puede estar vacio");
		}
		if (apellido.isEmpty() || apellido == null) {
			throw new MyException("El nombre no puede estar vacio");
		}
		if (username.isEmpty() || username == null) {
			throw new MyException("El nombre de usuario no puede estar vacío");
		}
		if (usuarioService.buscarXUserName(username) != null) {
			throw new MyException("El nombre de usuario ingresado ya existe, por favor ingrese otro");
		}
		if (password.isEmpty() || password == null) {
			throw new MyException("La contraseña no puede estar vacía");
		}
		if (password1.isEmpty() || password1 == null) {
			throw new MyException("La contraseña 2 no puede estar vacía");
		}
		if (!password.equals(password1)) {
			throw new MyException("Las contraseñas no coinciden");
		}

	}

	@Transactional
	public void delete(String id) throws Exception {
		repository.delete(buscarXId(id));
	}

	public Perfil buscarXId(String id) throws Exception {
		Optional<Perfil> optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new MyException("Id no encontrado");
		}

	}

	public List<Perfil> listaDePerfiles() {
		return repository.findAll();
	}

	@Transactional
	public void modificar(String id, String nombre, String apellido, MultipartFile foto, MultipartFile fotoPortada)
			throws Exception {

		Perfil perfil = buscarXId(id);

		perfil.setNombre(nombre);
		perfil.setApellido(apellido);

		if (foto != null) {
			perfil.setFoto(foto.getBytes());
		}
		if (fotoPortada != null) {
			perfil.setFotoPortada(fotoPortada.getBytes());
		}
		repository.save(perfil);

	}

	@Transactional
	public void guardarFoto(String id, MultipartFile foto) throws Exception {
		Perfil perfil = buscarXId(id);
		if (foto.getSize() == 0) {
			throw new MyException("primero debe cargar la foto y despues guardar");
		}
		perfil.setFoto(foto.getBytes());

		repository.save(perfil);

	}

	@Transactional
	public void guardarFotoPortada(String id, MultipartFile fotoPortada) throws Exception {
		Perfil perfil = buscarXId(id);
		if (fotoPortada != null) {
			perfil.setFotoPortada(fotoPortada.getBytes());
		}
		repository.save(perfil);

	}

	/*
	 * PARA CUANDO SEAN MAS DE UNA CONMEMORACION
	 * 
	 * @Transactional public void guardarConmemoracion(Conmemoracion conmemoracion,
	 * String id) throws Exception { Perfil perfil = buscarXId(id);
	 * if(perfil.getConmemoracion() == null || perfil.getConmemoracion().isEmpty())
	 * { List<Conmemoracion> conmemoraciones = new ArrayList<Conmemoracion>();
	 * conmemoraciones.add(conmemoracion); perfil.setConmemoracion(conmemoraciones);
	 * }else { List<Conmemoracion> conmemoraciones = perfil.getConmemoracion();
	 * conmemoraciones.add(conmemoracion); perfil.setConmemoracion(conmemoraciones);
	 * }
	 * 
	 * repository.save(perfil); }
	 */
	@Transactional
	public void guardarConmemoracion(Conmemoracion conmemoracion, String id) throws Exception {
		Perfil perfil = buscarXId(id);
		perfil.setConmemoracion(conmemoracion);

		repository.save(perfil);
	}

}
