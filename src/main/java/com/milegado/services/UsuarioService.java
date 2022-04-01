package com.milegado.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.milegado.entities.Usuario;
import com.milegado.enums.Role;
import com.milegado.exceptions.MyException;
import com.milegado.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;

	@Transactional
	public void save(String username, String password, String password1) throws Exception {
		validar(username, password, password1);
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setContraseña(encoder.encode(password));
		usuario.setRol(Role.USUARIO);
		
		repository.save(usuario);
			
	}

	private void validar(String username, String password, String password1) throws Exception{
		if(username.isEmpty() || username == null) {
			throw new MyException("El nombre de usuario no puede estar vacío");
		}
		if(buscarXUserName(username) != null) {
			throw new MyException("El nombre de usuario ingresado ya existe, por favor ingrese otro");
		}
		if(password.isEmpty() || password == null) {
			throw new MyException("La contraseña no puede estar vacía");
		}
		if(password1.isEmpty() || password1 == null) {
			throw new MyException("La contraseña 2 no puede estar vacía");
		}
		if(!password.equals(password1)) {
			throw new MyException("Las contraseñas no coinciden");
		}	
	}
	
	public Usuario buscarXUserName(String username) {
		return repository.buscarXUserName(username);
	}
	
	public List<Usuario> listarTodos() {
		return repository.findAll();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Usuario usuario = buscarXUserName(username);
			List<GrantedAuthority> authorities= new ArrayList<>();
			
			authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
			
			return new User(username, usuario.getContraseña(), authorities);
		}catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Usuario o contraseña no encontrado");
		}
	}
	
	public String obtenernombre(Authentication usuario) throws MyException {
		
		try {
			return usuario.getName();
		} catch (Exception e) {
			throw new MyException("error interno, al obtener nombre de usuario en UsuarioService");
		}

	}

	public Usuario buscaruserxid(String id) throws Exception{
		Optional<Usuario> respuesta= repository.findById(id) ;
		if(respuesta.isPresent()) {
			return respuesta.get();
		}else {
			throw new MyException("Id no encontrado");
		}
	}
	
}
