package br.com.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.algamoney.api.exception.PessoaInexistenteOuInativaException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request ) {

    String mensagem = messageSource.getMessage( "mensagem.invalida", null, LocaleContextHolder.getLocale() );

    List<Erro> erros = Arrays.asList( new Erro( mensagem ) );

    return handleExceptionInternal( ex, erros, headers, status, request );
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request ) {

    List<Erro> erros = criarListaDeErros( ex.getBindingResult() );

    return handleExceptionInternal( ex, erros, headers, status, request );
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({ InvalidDataAccessApiUsageException.class })
  public ResponseEntity<Object> handleInvalidDataAccessApiUsageException( InvalidDataAccessApiUsageException ex,
      WebRequest request ) {
    String mensagem =
        messageSource.getMessage( "parametro.parametro-desconhecido", null, LocaleContextHolder.getLocale() );

    List<Erro> erros = Arrays.asList( new Erro( mensagem ) );

    return handleExceptionInternal( ex, erros, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request );
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({ EmptyResultDataAccessException.class })
  public ResponseEntity<Object> handleEmptyResultDataAccessException( EmptyResultDataAccessException ex,
      WebRequest request ) {

    String mensagem = messageSource.getMessage( "recurso.nao-encontrado", null, LocaleContextHolder.getLocale() );

    List<Erro> erros = Arrays.asList( new Erro( mensagem ) );

    return handleExceptionInternal( ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request );
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({ DataIntegrityViolationException.class })
  public ResponseEntity<Object> handleDataIntegrityViolationException( DataIntegrityViolationException ex,
      WebRequest request ) {

    String mensagem =
        messageSource.getMessage( "recurso.problema-dados-enviados", null, LocaleContextHolder.getLocale() );

    List<Erro> erros = Arrays.asList( new Erro( mensagem ) );

    return handleExceptionInternal( ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request );
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({ PessoaInexistenteOuInativaException.class })
  public ResponseEntity<Object> handlePessoaInexistenteOuInativaException( PessoaInexistenteOuInativaException ex ) {

    String mensagem =
        messageSource.getMessage( "pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale() );

    List<Erro> erros = Arrays.asList( new Erro( mensagem ) );

    return ResponseEntity.badRequest().body( erros );
  }

  private List<Erro> criarListaDeErros( BindingResult bindingResult ) {
    List<Erro> erros = new ArrayList<>();

    for ( FieldError fieldError : bindingResult.getFieldErrors() ) {
      String mensagem = messageSource.getMessage( fieldError, LocaleContextHolder.getLocale() );
      erros.add( new Erro( mensagem ) );
    }

    return erros;
  }

  public static class Erro {

    private String mensagem;

    public Erro( String mensagem ) {
      this.mensagem = mensagem;
    }

    public String getMensagem() {
      return mensagem;
    }

  }
}
