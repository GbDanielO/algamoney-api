package br.com.algamoney.api.categoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algamoney.api.categoria.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
