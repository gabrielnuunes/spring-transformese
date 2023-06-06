package com.serasa.transformese.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serasa.transformese.modelo.Paciente;

@Repository
public interface RepositorioPaciente extends JpaRepository<Paciente, Long>{

}
