package com.milegado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milegado.entities.Conmemoracion;

@Repository
public interface ConmemoracionRepository extends JpaRepository<Conmemoracion, String>{

}
