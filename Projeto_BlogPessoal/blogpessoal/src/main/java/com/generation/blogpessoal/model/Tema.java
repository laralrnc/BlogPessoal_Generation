package com.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//identificando que esta classe criará uma tabela no db
@Entity
//identificando o nome da tabela
@Table(name = "tb_temas")

public class Tema {
	
	//criando atributos
	
	//identificando a chave primaria e o auto increment. GenerationType.IDENTITY = o db que irá gerar o número
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo descrição é obrigatório")
	private String descricao;
	
	//chave estrangeira do tipo list, pq um tema pode ter mais de uma postagem relacionada
	//anotação que cria a chave estrangeira
	//mappedBy = nomeia o relacionamento
	//CascadeType = relaciona os temas com todas as postagens. 
	//Ex: quando um tema é apagado todas as postagens relacionadas a ele são apagadas
	@OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tema")
	private List <Postagem> postagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	

}
