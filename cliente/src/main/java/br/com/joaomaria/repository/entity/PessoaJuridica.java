package br.com.joaomaria.repository.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.joaomaria.model.TipoPessoaEnum;

@Table(name = "pessoa_juridica")
@Entity
public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 1812792369377264233L;

	@Column(name = "razao_social")
	private String razao;

	@Column(name = "nome_fantasia")
	private String fantasia;

	@Column(name = "cnpj")
	private String cnpj;

	@ElementCollection
	@CollectionTable(name = "telefone", joinColumns = @JoinColumn(name = "id_pessoa"))
	@Column(name = "numero")
	private List<String> telefones;

	@Column(name = "site")
	private String site;

	public PessoaJuridica() {
		super.setFlTipoPessoa(TipoPessoaEnum.JURIDICA.getTipo());
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

}