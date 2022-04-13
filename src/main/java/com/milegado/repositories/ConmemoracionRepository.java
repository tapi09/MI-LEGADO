package com.milegado.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.milegado.entities.Conmemoracion;
import com.milegado.entities.Usuario;

@Repository
public interface ConmemoracionRepository extends JpaRepository<Conmemoracion, String>{
	
	@Query("SELECT c FROM Conmemoracion c , Usuario u WHERE u.id= :id")
	public List <Conmemoracion> buscarUltima(@Param("id") String id);
}
