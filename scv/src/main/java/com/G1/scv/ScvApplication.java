package com.G1.scv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.G1.scv.Controller.ClienteController;
import com.G1.scv.model.Usuario;
import com.G1.scv.model.UsuarioRepository;

@SpringBootApplication
public class ScvApplication {
	Logger logger = LogManager.getLogger(ClienteController.class);
	@Autowired
	UsuarioRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ScvApplication.class, args);
	}


	@Autowired
	public void inicializa() {
// 1-cadastrar as crendenciais do usuario
		Usuario usuario = new Usuario();
		usuario.setNome("Jose da Silva");
		usuario.setLogin("jose");
		usuario.setSenha(passwordEncoder.encode("123"));
		repository.save(usuario);
		usuario = repository.findByLogin("jose");
		logger.info(">>>>>> inicializacao da aplicacao => " + usuario.toString());
		usuario = new Usuario();
		usuario.setNome("Maria Silva");
		usuario.setLogin("maria");
		usuario.setSenha(passwordEncoder.encode("456"));
		repository.save(usuario);

	}
}
