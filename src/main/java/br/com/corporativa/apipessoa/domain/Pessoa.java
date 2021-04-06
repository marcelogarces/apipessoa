package br.com.corporativa.apipessoa.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	@Column(nullable = false,unique = true)
	@CPF(message = "CPF inválido!")
	private String cpf;	

	@NotBlank(message = "Nome deve ser preenchido!")
	@Size(min = 2,max = 40,message = "Nome deve ter no mínimo 2 e máximo 40 caracteres!")
	private String nome;
	
	@NotBlank(message = "e-mail deve ser preenchido.")
	@Email(message = "e-mail inválido!")
	private String email;

	private LocalDate dataNascimento;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
		
}
