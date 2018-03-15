package br.com.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.algamoney.api.categoria.model.Categoria;
import br.com.algamoney.api.categoria.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPeloCodigo( @PathVariable Long codigo ) {
        Optional<Categoria> optional = categoriaRepository.findById( codigo );
        if ( optional.isPresent() ) {
            return ResponseEntity.ok().body( optional.get() );
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria criar( @Valid @RequestBody Categoria categoria, HttpServletResponse response ) {

        Categoria categoriaSalva = categoriaRepository.save( categoria );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path( "/{codigo}" )
                .buildAndExpand( categoriaSalva.getCodigo() ).toUri();

        response.setHeader( "Location", uri.toASCIIString() );

        return categoria;
    }

    @PutMapping("/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public Categoria atualizar( @PathVariable Long codigo, @Valid @RequestBody Categoria categoria,
            HttpServletResponse response ) {

        Optional<Categoria> optional = categoriaRepository.findById( codigo );

        if ( !optional.isPresent() ) {
            throw new EmptyResultDataAccessException( 1 );
        }

        Categoria categoriaSalva = categoriaRepository.save( categoria );

        return categoriaSalva;
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long codigo ) {
        categoriaRepository.deleteById( codigo );
    }

}
