package com.milegado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.milegado.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	@Query("SELECT u FROM Usuario u WHERE u.username = :username")
	Usuario buscarXUserName(String username);
}
