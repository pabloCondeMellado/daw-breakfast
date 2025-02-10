package com.daw.web.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Usuario;
import com.daw.services.UsuarioServices;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioServices usuarioServices;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listaUsuario(){
		return ResponseEntity.ok(this.usuarioServices.findAll());
	}
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable int idUsuario){
		Optional<Usuario> usuario = this.usuarioServices.findById(idUsuario);
		if(usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(usuario.get());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario){
		return new ResponseEntity<Usuario>(this.usuarioServices.create(usuario), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idUsuario}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable int idUsuario, @RequestBody Usuario usuario){
		if(idUsuario != usuario.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.usuarioServices.exitsUsuario(idUsuario)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.usuarioServices.update(usuario));
	}
	
	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable int idUsuario){
		if(this.usuarioServices.delete(idUsuario)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	@PutMapping("/{idUsuario}/password")
	public ResponseEntity<Usuario> updatePassword(@PathVariable int idUsuario, @RequestParam String password){
		if(this.usuarioServices.exitsUsuario(idUsuario)) {
			return ResponseEntity.ok(this.usuarioServices.updatePassword(idUsuario, password));
		}
		return ResponseEntity.notFound().build();
	}
}
