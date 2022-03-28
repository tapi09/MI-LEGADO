package com.milegado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milegado.entities.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, String>{

}
