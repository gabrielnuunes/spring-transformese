package com.serasa.transformese.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.serasa.transformese.modelo.Paciente;
import com.serasa.transformese.repositorio.RepositorioPaciente;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "http://localhost:4200")
public class PacienteControlador {

	@Autowired
	private RepositorioPaciente repositorioPaciente;
	
	@GetMapping
	public List<Paciente> listar() {
		
		return repositorioPaciente.findAll();
	}  
	
	@PostMapping
	public Paciente adicionar(@RequestBody Paciente paciente) {
		
		return repositorioPaciente.save(paciente);
	}
	
	@GetMapping("/{pacienteId}")
	public ResponseEntity<Paciente> buscar(@PathVariable Long pacienteId) {
		
		Optional<Paciente> paciente = repositorioPaciente.findById(pacienteId);
		
		if(paciente.isPresent()) {
			
			return ResponseEntity.ok(paciente.get());
		} 
			
			return ResponseEntity.notFound().build();		
	}	
	
	@DeleteMapping("/{pacienteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long pacienteId) {
		
		repositorioPaciente.deleteById(pacienteId);
	}
	
	@PutMapping("/{pacienteId}")
	public ResponseEntity<Paciente> atualizar (@PathVariable Long pacienteId, @RequestBody Paciente paciente) {
		
		// Armazenando a clinica localizada no bando de dados
		Optional<Paciente> pacienteAtual = repositorioPaciente.findById(pacienteId);
		
		if (pacienteAtual.isPresent()) {
			
			// Aqui estamos copiando os dados de uma clinica para outra, exceto o id
			
			BeanUtils.copyProperties(paciente, pacienteAtual.get(), "id" );	
					
			
			Paciente pacienteAtualizado = repositorioPaciente.save(pacienteAtual.get());
			
			return ResponseEntity.ok(pacienteAtualizado);			
			
		}
		
		return ResponseEntity.notFound().build();
		
		
	}
	
}
