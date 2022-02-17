package com.generation.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

//identificando que a interfaace é do tipo repository
//criando uma herança
// Postagem = objeto (classe que criei a tabela) e Long = Chave primária da tabela tb_postagem
//uma interface para cada tabela
@Repository
public interface PostagemRepository extends JpaRepository <Postagem, Long> {

}
