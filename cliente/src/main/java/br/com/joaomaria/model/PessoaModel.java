package br.com.joaomaria.model;

public class PessoaModel {

	private Long id;
	private String flTipoPessoa;
	private String nome;
	private String documento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlTipoPessoa() {
		return flTipoPessoa;
	}

	public void setFlTipoPessoa(String flTipoPessoa) {
		this.flTipoPessoa = flTipoPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

}
