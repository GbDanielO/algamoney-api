package br.com.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.algamoney.api.pessoa.model.Pessoa;
import br.com.algamoney.api.pessoa.service.PessoaService;

@RestController()
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaService.listar();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo( @PathVariable Long codigo ) {
        return pessoaService.buscarPeloCodigo( codigo );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criar( @Valid @RequestBody Pessoa pessoa, HttpServletResponse response ) {
        return pessoaService.criar( pessoa, response );
    }

    @PutMapping("/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public Pessoa atualizar( @PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa,
            HttpServletResponse response ) {
        return pessoaService.atualizar( codigo, pessoa, response );
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long codigo ) {
        pessoaService.delete( codigo );
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarStatusPessoa( @PathVariable Long codigo, @RequestBody Boolean ativo ) {
        pessoaService.atualizarStatusPessoa( codigo, ativo );
    }
}
