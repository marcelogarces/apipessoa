package br.com.corporativa.apipessoa.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.corporativa.apipessoa.domain.Pessoa;
import br.com.corporativa.apipessoa.dto.PessoaEdicaoDTO;
import br.com.corporativa.apipessoa.service.PessoaService;

@RestController
@RequestMapping(value = "/api/v1/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService; 
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> inserirPessoa(@Valid @RequestBody Pessoa pessoa) throws Exception {
		pessoaService.inserirPessoa(pessoa);  
		Object body = "Pessoa criada com sucesso!";
		return new ResponseEntity<Object>(body, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/cpf/{cpf}" ,method = RequestMethod.PUT)
	public ResponseEntity<Object> alterarPessoa(@Valid @RequestBody PessoaEdicaoDTO pessoa,@PathVariable String cpf) throws Exception{
		pessoaService.alterarPessoa(pessoa,cpf);
		Object body = "Pessoa atualizada com sucesso!";
		return new ResponseEntity<Object>(body, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/cpf/{cpf}",method = RequestMethod.DELETE)
	public ResponseEntity<?> removerPessoa(@PathVariable String cpf) {
		
		  Optional<Pessoa> optional = pessoaService.obterPessoaPorCPF(cpf);
		
		  if(optional.isPresent()) {
			  	pessoaService.removerPessoa(optional.get());
			  	Object body = "Pessoa removida com sucesso!";
				return new ResponseEntity<Object>(body, HttpStatus.OK);
		  }else {
			  Object body = "Pessoa não encontrada!";
			  return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		  }
	}
	
	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> obterPessoaPorCPF(@PathVariable String cpf) {
		
		Optional<Pessoa> optionalPessoa = pessoaService.obterPessoaPorCPF(cpf);
		
		if(optionalPessoa.isPresent()) {
			return ResponseEntity.ok().body(optionalPessoa.get());
		}else {
			Object body = "Pessoa não encontrada!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listarPessoas() {
			List<Pessoa> pessoas = pessoaService.listarPessoas();
		if(pessoas!=null && !pessoas.isEmpty()) {
			return ResponseEntity.ok().body(pessoas);
		}else {
			Object body = "Não existem pessoas cadastradas!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
}
