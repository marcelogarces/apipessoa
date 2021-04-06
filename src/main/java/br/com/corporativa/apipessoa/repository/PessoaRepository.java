package br.com.corporativa.apipessoa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.corporativa.apipessoa.domain.Pessoa;

public interface PessoaRepository  extends CrudRepository<Pessoa, Long>{

	@Query("SELECT p from Pessoa p where p.cpf = :cpf")
	Optional<Pessoa> obterPessoaPorCPF(String cpf);
}
