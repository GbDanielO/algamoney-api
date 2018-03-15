package br.com.algamoney.api.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 3173075664305057040L;

  public PessoaInexistenteOuInativaException() {
  }

  public PessoaInexistenteOuInativaException( String string ) {
    super( string );
  }

}
