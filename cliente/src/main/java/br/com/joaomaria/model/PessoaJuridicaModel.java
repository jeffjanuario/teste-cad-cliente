package br.com.joaomaria.model;

import java.util.List;

public class PessoaJuridicaModel {

	private Long id;
	private String razao;
	private String fantasia;
	private String cnpj;
	private List<String> telefones;
	private String site;
	private UsuarioModel usuarioCadastro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public UsuarioModel getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(UsuarioModel usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
}