package br.com.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algamoney.api.lancamento.model.Lancamento;
import br.com.algamoney.api.lancamento.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

  @Autowired
  private LancamentoService lancamentoService;

  @GetMapping
  public List<Lancamento> listar() {
    return lancamentoService.listar();
  }

  @GetMapping("/filtrar")
  public Page<Lancamento> filtrar( Lancamento lancamento, Pageable pageable ) {
    return lancamentoService.filtrar( lancamento, pageable );
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Lancamento> buscarPeloCodigo( @PathVariable Long codigo ) {
    return lancamentoService.buscarPeloCodigo( codigo );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Lancamento criar( @Valid @RequestBody Lancamento lancamento, HttpServletResponse response ) {
    return lancamentoService.criar( lancamento, response );
  }

  @PutMapping("/{codigo}")
  @ResponseStatus(HttpStatus.OK)
  public Lancamento atualizar( @Valid @RequestBody Lancamento lancamento, @PathVariable Long codigo,
      HttpServletResponse response ) {
    return lancamentoService.atualizar( lancamento, codigo, response );
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar( @PathVariable Long codigo ) {
    lancamentoService.deletar( codigo );
  }

}
