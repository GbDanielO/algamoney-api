package br.com.algamoney.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TemporalType;

public class Utils {

  public static String getDataFormatada( final Date date, final String mascara ) {
    if ( date != null ) {
      final SimpleDateFormat dateFormat = new SimpleDateFormat( mascara, new Locale( "pt", "BR" ) );

      return dateFormat.format( date );
    }
    return "";
  }

  public static void setarParametroWhere( final Map<String, Object> mapWhere, final Query query ) {
    if ( mapWhere != null && !mapWhere.isEmpty() ) {
      final Set<String> lstParametros = mapWhere.keySet();

      if ( lstParametros != null && !lstParametros.isEmpty() ) {
        for ( final String parametro : lstParametros ) {
          final Object valorKey = mapWhere.get( parametro );

          if ( valorKey.getClass().equals( Date.class ) ) {
            query.setParameter( parametro, (Date) valorKey, TemporalType.DATE );
          } else {
            query.setParameter( parametro, valorKey );
          }
        }
      }
    }
  }
}
