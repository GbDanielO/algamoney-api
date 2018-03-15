package br.com.algamoney.api.pessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algamoney.api.pessoa.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
