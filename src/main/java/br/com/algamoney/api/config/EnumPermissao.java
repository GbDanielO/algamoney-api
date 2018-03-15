package br.com.algamoney.api.config;

public enum EnumPermissao {

    WRITE("write"),

    READ("read"),

    UDATE("update"),

    DELETE("delete");

    private String descricao;

    private EnumPermissao( String descricao ) {
        this.descricao = descricao;
    }

    public String getDesricao() {
        return this.descricao;
    }

}
