package br.com.joaomaria.model;

public enum TipoPessoaEnum {
	FISICA("F", "Física"), JURIDICA("J", "Jurídica");

	private String tipo;
	private String descricao;

	TipoPessoaEnum(String tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}

}
