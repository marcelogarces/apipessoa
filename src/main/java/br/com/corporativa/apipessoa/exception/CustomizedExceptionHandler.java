package br.com.corporativa.apipessoa.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<Error> handleBusinessExceptions(BusinessException ex, WebRequest request) {
	  Error errorDetails = new Error(LocalDate.now(), ex.getMessage(),
	      request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
	  Error errorDetails = new Error(LocalDate.now(), ex.getMessage(),
	      request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Error> handleExceptions(Exception ex, WebRequest request) {
	  Error errorDetails = new Error(LocalDate.now(), "Ocorreu erro interno, favor contactar a equipe responsável.",
	      request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler({
		AccessDeniedException.class
	})
	public ResponseEntity<Error> accessDenied(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error(LocalDate.now(), "Acesso negado",request.getDescription(false)));		
	}
	
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDate.now());
	        body.put("status", status.value());

	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());
	        body.put("errors", errors);
	        return new ResponseEntity<>(body, headers, status);

	    }
}
