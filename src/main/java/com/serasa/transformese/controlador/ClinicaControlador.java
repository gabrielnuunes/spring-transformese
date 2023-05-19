package com.serasa.transformese.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.serasa.transformese.modelo.Clinica;
import com.serasa.transformese.repositorio.RepositorioClinica;

@RestController // Executa os métodos e devolve as respostas
@RequestMapping("/clinicas") // Localizar 
public class ClinicaControlador {

	@Autowired
	private RepositorioClinica repositorioClinica;
	
	@GetMapping
	public List<Clinica> listar() {
		
		return repositorioClinica.findAll();		
	}
	
//	@PostMapping
//	@ResponseStatus(value = HttpStatus.CREATED)
//	public void adicionar(@RequestBody Clinica clinica) {
//		
//		repositorioClinica.save(clinica);
//	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Clinica adicionar(@RequestBody Clinica clinica) {
		
		return repositorioClinica.save(clinica);
		
	}
	
	// www.serasa.com/clinicas/id
	
	// localhost:8080/clinicas/2
	
	@DeleteMapping("/{clinicaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long clinicaId) {
		
		repositorioClinica.deleteById(clinicaId);
	}
	
	@GetMapping("/{clinicaId}")
	public ResponseEntity<Clinica> buscar(@PathVariable Long clinicaId) {
		
		Optional<Clinica> clinica = repositorioClinica.findById(clinicaId);	
		
		
			if( clinica.isPresent() ) {
				
				return ResponseEntity.ok(clinica.get());
			} 
			
			return ResponseEntity.notFound().build();				
	}
	
	@PutMapping("/{clinicaId}")
	public ResponseEntity<Clinica> atualizar (@PathVariable Long clinicaId, @RequestBody Clinica clinica) {
		
		Optional<Clinica> clinicaAtual = repositorioClinica.findById(clinicaId);
		
		if (clinicaAtual.isPresent()) {
			
			// Aqui estamos copiando os dados de uma clinica para outra, exceto o id
			BeanUtils.copyProperties(clinica, clinicaAtual.get(), "id" );	
					
			
			Clinica clinicaAtualizada = repositorioClinica.save(clinicaAtual.get());
			
			return ResponseEntity.ok(clinicaAtualizada);			
			
		}
		
		return ResponseEntity.notFound().build();
		
		
	}
	
	
	
}
