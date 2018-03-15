package br.com.algamoney.api.lancamento.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.algamoney.api.exception.PessoaInexistenteOuInativaException;
import br.com.algamoney.api.lancamento.model.Lancamento;
import br.com.algamoney.api.lancamento.repository.LancamentoRepository;
import br.com.algamoney.api.pessoa.model.Pessoa;
import br.com.algamoney.api.pessoa.repository.PessoaRepository;
import br.com.algamoney.api.pessoa.service.PessoaService;

@Service
public class LancamentoService {

  @Autowired
  private LancamentoRepository lancamentoRepository;

  @Autowired
  private PessoaService pessoaService;

  @Autowired
  private PessoaRepository pessoaRepository;

  public List<Lancamento> listar() {
    return lancamentoRepository.findAll();
  }

  public Page<Lancamento> filtrar( Lancamento lancamento, Pageable pageable ) {
    return lancamentoRepository.filtrar( lancamento, pageable );
  }

  public ResponseEntity<Lancamento> buscarPeloCodigo( Long codigo ) {
    Optional<Lancamento> optional = lancamentoRepository.findById( codigo );

    if ( optional.isPresent() ) {
      return ResponseEntity.ok().body( optional.get() );
    }

    return ResponseEntity.notFound().build();
  }

  public Lancamento criar( @Valid Lancamento lancamento, HttpServletResponse response ) {
    validaPessoaInexistenteOuInativa( lancamento );

    Lancamento lancamentoSalvo = lancamentoRepository.save( lancamento );

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path( "/{codigo}" )
        .buildAndExpand( lancamentoSalvo.getCodigo() ).toUri();

    response.setHeader( "Location", uri.toASCIIString() );

    return lancamentoSalvo;
  }

  public Lancamento atualizar( @Valid Lancamento lancamento, Long codigo, HttpServletResponse response ) {
    validaPessoaInexistenteOuInativa( lancamento );

    Optional<Lancamento> optional = lancamentoRepository.findById( codigo );

    if ( !optional.isPresent() ) {
      throw new EmptyResultDataAccessException( 1 );
    }

    Lancamento lancamentoSalvo = lancamentoRepository.save( lancamento );

    return lancamentoSalvo;
  }

  public void deletar( Long codigo ) {
    lancamentoRepository.deleteById( codigo );
  }

  private void validaPessoaInexistenteOuInativa( Lancamento lancamento ) {
    Pessoa pessoa;
    try {
      pessoa = pessoaRepository.getOne( lancamento.getPessoa().getCodigo() );
      if ( pessoa == null || !pessoa.getAtivo() ) {
        throw new PessoaInexistenteOuInativaException();
      }
    } catch ( Exception e ) {
      throw new PessoaInexistenteOuInativaException();
    }
  }

}
