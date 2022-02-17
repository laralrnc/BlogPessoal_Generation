package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;


// identificando que esta classe criará uma tabela no db
@Entity
//identificando o nome da tabel
@Table(name = "tb_postagens")
public class Postagem {
	
	//criando atributos
	
	//identificando a chave primaria e o auto increment. GenerationType.IDENTITY = o db que irá gerar o número
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//configuração do título. NOTNULL e não pode estar vazio e tamanho
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 5 e no máximo 100 caracteres")
	private String titulo;
	
	//configuração do texto. NOTNULL e não pode estar vazio e tamanho
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 10, max = 1000, message = "O atributo texto deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String texto;
	
	//configurando a data - o sistema atualizará sozinho
	@UpdateTimestamp
	private LocalDateTime data;
	
	//Métodos Get and Set
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}

}
