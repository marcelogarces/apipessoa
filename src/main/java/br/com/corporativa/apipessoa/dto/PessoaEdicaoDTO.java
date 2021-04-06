package br.com.corporativa.apipessoa.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PessoaEdicaoDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome deve ser preenchido!")
	@Size(min = 2,max = 40,message = "Nome deve ter no mínimo 2 e máximo 40 caracteres!")
	private String nome;
	
	@NotBlank(message = "e-mail deve ser preenchido.")
	@Email(message = "e-mail inválido!")
	private String email;
	
	private LocalDate dataNascimento;
	
	
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
