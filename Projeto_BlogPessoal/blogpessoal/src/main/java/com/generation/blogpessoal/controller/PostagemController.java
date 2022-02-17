package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
//configuração de link front e back, 
//caso não tenha essa configuração o front e back só irá funcionar se estiverem configurados na mesma máquina
//possibilita que cada parte funcione em um servidor diferente
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	//criação de objeto para liberar acesso aos metodos de manipulação dos dados objeto = postagemRepository
	@Autowired
	private PostagemRepository postagemRepository;
	
	// criação do metodo de exibição dos dados da tabelas
	@GetMapping 
	public ResponseEntity <List <Postagem>> getAll()
	{
		//findAll = exibe todos os dados =select *from tb_postagens
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	

}
