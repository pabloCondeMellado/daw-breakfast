package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.UsuarioCrudRepository;
import com.daw.persistence.entities.Usuario;

@Service
public class UsuarioServices {
	@Autowired
	private UsuarioCrudRepository usuarioCrudRepository;
	
	public List<Usuario> findAll(){
		return this.usuarioCrudRepository.findAll();
	}
	
	public Optional<Usuario> findById(int idUsuario){
		return this.usuarioCrudRepository.findById(idUsuario);
	}
	
	public Boolean exitsUsuario(int idUsuario) {
		return this.usuarioCrudRepository.existsById(idUsuario);
	}
	
	public Usuario create(Usuario usuario) {
		return this.usuarioCrudRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario) {
		return this.usuarioCrudRepository.save(usuario);
	}
	
	public Boolean delete(int idUsuario) {
		Boolean result = false;
		if(this.usuarioCrudRepository.existsById(idUsuario)) {
			this.usuarioCrudRepository.deleteById(idUsuario);
			result = true;
		}
		
		return result;
	}
	public Usuario updatePassword(int idUsuario, String newPassword) {
		Usuario usuario = this.usuarioCrudRepository.findById(idUsuario).get();
		
		usuario.setPassword(newPassword);
		usuario = this.usuarioCrudRepository.save(usuario);
		
		return usuario;
	}
}
