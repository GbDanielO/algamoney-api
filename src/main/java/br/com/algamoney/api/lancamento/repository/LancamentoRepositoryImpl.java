package br.com.algamoney.api.lancamento.repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.algamoney.api.lancamento.model.Lancamento;
import br.com.algamoney.api.utils.Utils;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @SuppressWarnings("unchecked")
  public Page<Lancamento> filtrar( Lancamento lancamento, Pageable pageable ) {

    Map<String, Object> mapParam = new HashMap<>();

    StringBuilder sb = new StringBuilder();
    sb.append( "SELECT lan FROM  Lancamento lan JOIN FETCH lan.pessoa JOIN FETCH lan.categoria WHERE 1 = 1 " );

    if ( lancamento.getDescricao() != null && !lancamento.getDescricao().isEmpty() ) {
      sb.append( " AND LOWER(lan.descricao) LIKE LOWER(:descricao) " );
      mapParam.put( "descricao", "%" + lancamento.getDescricao() + "%" );
    }
    if ( lancamento.getDataVencimentoDe() != null ) {
      sb.append( " AND lan.vencimento >= :dataDe " );
      mapParam.put( "dataDe", lancamento.getDataVencimentoDe() );
    }
    if ( lancamento.getDataVencimentoAte() != null ) {
      sb.append( " AND lan.vencimento <= :dataAte " );
      mapParam.put( "dataAte", lancamento.getDataVencimentoAte() );
    }

    Integer count = getCount( sb.toString(), mapParam );

    Query query = entityManager.createQuery( sb.toString() );
    Utils.setarParametroWhere( mapParam, query );

    query.setFirstResult( pageable.getPageNumber() * pageable.getPageSize() );
    query.setMaxResults( pageable.getPageSize() );

    return new PageImpl<Lancamento>( query.getResultList(), pageable, count );
  }

  private Integer getCount( String consulta, Map<String, Object> mapParam ) {
    StringBuilder sb = new StringBuilder();
    sb.append( consulta );
    Query query = entityManager.createQuery( sb.toString() );
    Utils.setarParametroWhere( mapParam, query );
    return query.getResultList().size();
  }

}
