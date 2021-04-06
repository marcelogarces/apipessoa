package br.com.corporativa.apipessoa.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.corporativa.apipessoa.domain.Pessoa;
import br.com.corporativa.apipessoa.dto.PessoaEdicaoDTO;
import br.com.corporativa.apipessoa.exception.BusinessException;
import br.com.corporativa.apipessoa.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Optional<Pessoa> obterPessoaPorId(Long id) {

		return pessoaRepository.findById(id);
	}

	public Optional<Pessoa> obterPessoaPorCPF(String cpf) {

		return pessoaRepository.obterPessoaPorCPF(cpf);
	}

	public void inserirPessoa(Pessoa pessoa) throws Exception {
		
		if(pessoa.getDataNascimento().isAfter(LocalDate.now()))throw new IllegalArgumentException("Data de nascimento com data futura!");
		Optional<Pessoa> optional = pessoaRepository.obterPessoaPorCPF(pessoa.getCpf());
		if (!optional.isPresent()) {
			pessoaRepository.save(pessoa);
		} else {
			throw new BusinessException("CPF já cadastrado!");
		}
	}
	
	public void alterarPessoa(PessoaEdicaoDTO pessoa,String cpf) throws Exception {

		Optional<Pessoa> optional = pessoaRepository.obterPessoaPorCPF(cpf);
		if (optional.isPresent()) {
			Pessoa pessoaDB = optional.get();
			pessoaDB.setNome(pessoa.getNome());
			pessoaDB.setEmail(pessoa.getEmail());
			pessoaDB.setDataNascimento(pessoa.getDataNascimento());
			pessoaRepository.save(pessoaDB);
		} else {
			throw new BusinessException("Pessoa com CPF informado não encontrado para edição!");
		}
	}

	
	public void removerPessoa(Pessoa pessoa) {
			pessoaRepository.delete(pessoa);
	}

	public List<Pessoa> listarPessoas() {
		return (List<Pessoa>) pessoaRepository.findAll();
	}

}
