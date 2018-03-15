package br.com.algamoney.api.lancamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algamoney.api.lancamento.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
