package br.com.algamoney.api.lancamento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.algamoney.api.lancamento.model.Lancamento;

public interface LancamentoRepositoryQuery {

  Page<Lancamento> filtrar( Lancamento lancamento, Pageable pageable );
}
