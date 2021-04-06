package br.com.corporativa.apipessoa.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.corporativa.apipessoa.domain.Pessoa;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class PessoaControllerTest {
	
	
	 @Autowired
	 private WebApplicationContext wac;

	 @Autowired
	 protected MockMvc mockMvc;
	
	 @InjectMocks
	 private PessoaController pessoaControllerMock;
	 
	 @Mock
	 private Pessoa pessoa;
	 
	 
	 @Test
	 @Order(1)
	 public void inserirPessoaSucessoTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestSucesso()))
	                .andExpect(content().string("Pessoa criada com sucesso!"))
	                .andExpect(status().isCreated());
	  }
	 
	 @Test
	 @Order(2)
	 public void validarPessoaDuplicadaTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestSucesso()).characterEncoding("UTF-8"))
	                .andExpect(content().string(containsString("CPF já cadastrado!")))
	                .andExpect(status().isBadRequest());
	  }
	 
	 @Test
	 @Order(3)
	 public void validarPessoaComCPFInvalidoTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestCPFInvalido()).characterEncoding("UTF-8"))
	                .andExpect(content().string(containsString("CPF inválido!")))
	                .andExpect(status().isBadRequest());
	 }

	 @Test
	 @Order(4)
	 public void validarPessoaComEmailEmBrancoTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestEmailEmBranco()).characterEncoding("UTF-8"))
	                .andExpect(content().string(containsString("e-mail deve ser preenchido.")))
	                .andExpect(status().isBadRequest());
	 }
	 
	 @Test
	 @Order(5)
	 public void validarPessoaComEmailInvalidoTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestEmailInvalido()).characterEncoding("UTF-8"))
	                .andExpect(content().string(containsString("e-mail inválido!")))
	                .andExpect(status().isBadRequest());
	 }

	 @Test
	 @Order(6)
	 public void validarPessoaComNomeEmBrancoTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                       	.contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestNomeEmBranco()).characterEncoding("UTF-8"))
	                		.andExpect(content().string(containsString("Nome deve ser preenchido!")))
	                		.andExpect(status().isBadRequest());
	 }
	 
	 @Test
	 @Order(7)
	 public void validarPessoaComNomeInvalidoTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestNomeInvalido()).characterEncoding("UTF-8"))
		 					.andExpect(content().string(containsString("Nome deve ter no mínimo 2 e máximo 40 caracteres!")))
		 					.andExpect(status().isBadRequest());
	 }

	 @Test
	 @Order(8)
	 public void validarPessoaComDataNascimentoInvalidaTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestDataNascimentoInvalida()).characterEncoding("UTF-8"))
		 					.andExpect(status().isBadRequest());
	 }
	 
	 @Test
	 @Order(9)
	 public void validarPessoaComDataNascimentoFuturaTest() throws Exception {
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestDataNascimentoDataFutura()).characterEncoding("UTF-8"))
		 					.andExpect(content().string(containsString("Data de nascimento com data futura!")))
		 					.andExpect(status().isBadRequest());
	 }
	 
	 
	 
	 @Test
	 @Order(10)
	 public void alterarPessoaNomeSucessoTest() throws Exception {
		 mockMvc.perform(
	                put(("/api/v1/pessoa/cpf/14965951069"))
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestAlteracaoNomePessoaSucesso()))
	                .andExpect(content().string("Pessoa atualizada com sucesso!"))
	                .andExpect(status().isOk());
	  }
	 
	 
	 @Test
	 @Order(11)
	 public void consultarPessoaComNomeAtualizadoTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                get(("/api/v1/pessoa/cpf/14965951069"))
	                        .contentType(MediaType.APPLICATION_JSON));
	               
	                 response.andExpect(status().isOk())        
	                .andExpect(content().string(containsString("João Gomes Pereira")));
	 }
	 
	 @Test
	 @Order(12)
	 public void deletarPessoaTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                delete(("/api/v1/pessoa/cpf/14965951069"))
	                        .contentType(MediaType.APPLICATION_JSON));
	                 response.andExpect(status().isOk())        
	                 .andExpect(content().string("Pessoa removida com sucesso!"));
	 }
	 
	 
	 @Test
	 @Order(13)
	 public void consultarPessoaDeletadaTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                get(("/api/v1/pessoa/cpf/14965951069"))
	                        .contentType(MediaType.APPLICATION_JSON));
	                 response.andExpect(status().isNotFound())        
	                 .andExpect(content().string("Pessoa não encontrada!"));
	 }

	 
	 @Test
	 @Order(14)
	 public void consultarListagemComPessoasTest() throws Exception {
		
		 mockMvc.perform(
	                post("/api/v1/pessoa")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestSucessoAddListagem()))
	                .andExpect(content().string("Pessoa criada com sucesso!"))
	                .andExpect(status().isCreated());
		 
		 
		ResultActions response = mockMvc.perform(
	                get(("/api/v1/pessoa"))
	                        .contentType(MediaType.APPLICATION_JSON));
	                 response.andExpect(status().isOk())        
	                 .andExpect(content().string(containsString("Pedro da Silva")));
	 }
	
	 
	 private String getPayloadRequestSucesso() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"joao@gmail.com\",\r\n" + 
		 		"  \"nome\": \"João da silva\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestSucessoAddListagem() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"58625151000\",\r\n" + 
		 		"  \"dataNascimento\": \"1999-03-20\",\r\n" + 
		 		"  \"email\": \"pedro@gmail.com\",\r\n" + 
		 		"  \"nome\": \"Pedro da Silva\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestAlteracaoNomePessoaSucesso() {
		 return "{\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"joao@gmail.com\",\r\n" + 
		 		"  \"nome\": \"João Gomes Pereira\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestCPFInvalido() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951123\",\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"joao@gmail.com\",\r\n" + 
		 		"  \"nome\": \"João da silva\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestEmailEmBranco() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"\",\r\n" + 
		 		"  \"nome\": \"João da silva\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestEmailInvalido() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"joao$gmail,com\",\r\n" + 
		 		"  \"nome\": \"João da silva\"\r\n" + 
		 		"}";
	 }

	 
	 private String getPayloadRequestNomeEmBranco() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"\",\r\n" + 
		 		"  \"nome\": \"\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestNomeInvalido() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"1994-02-20\",\r\n" + 
		 		"  \"email\": \"joao$gmail,com\",\r\n" + 
		 		"  \"nome\": \"J\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestDataNascimentoInvalida() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"199-0-20\",\r\n" + 
		 		"  \"email\": \"joao@gmail.com\",\r\n" + 
		 		"  \"nome\": \"João da silva\"\r\n" + 
		 		"}";
	 }
	 
	 private String getPayloadRequestDataNascimentoDataFutura() {
		 return "{\r\n" + 
		 		"  \"cpf\": \"14965951069\",\r\n" + 
		 		"  \"dataNascimento\": \"2041-02-20\",\r\n" + 
		 		"  \"email\": \"joao@gmail.com\",\r\n" + 
		 		"  \"nome\": \"João da silva\"\r\n" + 
		 		"}";
	 }


	 
	 @BeforeEach
	 public void setUp() {
	    mockMvc = MockMvcBuilders
	        .webAppContextSetup(wac)
	        .addFilter((request, response, chain) -> {
	          response.setCharacterEncoding("UTF-8");
	          chain.doFilter(request, response);
	        }, "/*")
	        .build();
	  }

}
