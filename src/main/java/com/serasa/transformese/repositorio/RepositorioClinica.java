package com.serasa.transformese.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serasa.transformese.modelo.Clinica;

@Repository
public interface RepositorioClinica extends JpaRepository<Clinica, Long>{

	
}
