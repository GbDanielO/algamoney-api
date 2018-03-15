package br.com.algamoney.api.pessoa.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.algamoney.api.pessoa.model.Pessoa;
import br.com.algamoney.api.pessoa.repository.PessoaRepository;

@Service
public class PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  public List<Pessoa> listar() {
    return pessoaRepository.findAll();
  }

  public ResponseEntity<Pessoa> buscarPeloCodigo( Long codigo ) {

    Optional<Pessoa> optional = pessoaRepository.findById( codigo );

    if ( optional.isPresent() ) {
      return ResponseEntity.ok().body( optional.get() );
    }

    return ResponseEntity.notFound().build();
  }

  public Pessoa criar( Pessoa pessoa, HttpServletResponse response ) {

    Pessoa pessoaSalva = pessoaRepository.save( pessoa );

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path( "/{codigo}" )
        .buildAndExpand( pessoaSalva.getCodigo() ).toUri();

    response.setHeader( "Location", uri.toASCIIString() );

    return pessoaSalva;
  }

  public Pessoa atualizar( Long codigo, Pessoa pessoa, HttpServletResponse response ) {

    Optional<Pessoa> optional = pessoaRepository.findById( codigo );

    if ( !optional.isPresent() ) {
      throw new EmptyResultDataAccessException( 1 );
    }

    Pessoa pessoaSalva = pessoaRepository.save( pessoa );

    return pessoaSalva;
  }

  public void delete( Long codigo ) {
    pessoaRepository.deleteById( codigo );
  }

  public void atualizarStatusPessoa( Long codigo, Boolean ativo ) {

    Optional<Pessoa> optional = buscarPessoaPeloCodigo( codigo );

    if ( !optional.isPresent() ) {
      throw new EmptyResultDataAccessException( 1 );
    }

    Pessoa pessoa = optional.get();
    pessoa.setAtivo( ativo );
    pessoaRepository.save( pessoa );

  }

  public Optional<Pessoa> buscarPessoaPeloCodigo( Long codigo ) {
    return pessoaRepository.findById( codigo );
  }
}
