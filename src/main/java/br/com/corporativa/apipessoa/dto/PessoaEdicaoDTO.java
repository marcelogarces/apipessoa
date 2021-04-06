package br.com.corporativa.apipessoa.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class PessoaEdicaoDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String nome;
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
