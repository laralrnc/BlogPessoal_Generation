package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository <Tema, Long> {

	// select * from tb_postagens where descricao like %descricao%;
	//select = find
	// * = All
	//By = where
	// Containing = like. Containing compativel com qualquer banco de dados, pode ser o like para MySQL
	//IgnoreCase = não diferenciar maiusculo de minusculo
	//String descricao = especifica a coluna
	public List <Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
	

	
}
