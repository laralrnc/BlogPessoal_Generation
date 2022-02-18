package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

//identificando que a interfaace é do tipo repository
//criando uma herança
// Postagem = objeto (classe que criei a tabela) 
//e Long = Chave primária da tabela tb_postagem
//uma interface para cada tabela
//herdar todos os metodos responsaveis por manipula do banco de dados (JPAREPOSITORY) 
//proprio do Spring
//metodo não padrão devem ser criados aqui
@Repository
public interface PostagemRepository extends JpaRepository <Postagem, Long> {
	
	// select * from tb_postagens where titulo like %titulo%;
	//select = find
	// * = All
	//By = where
	// Containing = like. Containing compativel com qualquer banco de dados, pode ser o like para MySQL
	//IgnoreCase = não diferenciar maiusculo de minusculo
	//String titulo = especifica a coluna
	List <Postagem> findAllByTituloContainingIgnoreCase(String titulo);

}
