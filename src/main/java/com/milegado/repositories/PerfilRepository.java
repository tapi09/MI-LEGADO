package com.milegado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milegado.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {

}
