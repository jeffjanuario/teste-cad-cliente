package br.com.joaomaria.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.joaomaria.model.TipoPessoaEnum;

@Table(name = "pessoa_fisica")
@Entity
public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = 8293418959405581584L;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "dt_nascimento")
	private Date dtNascimento;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "email")
	private String email;

	public PessoaFisica() {
		super.setFlTipoPessoa(TipoPessoaEnum.FISICA.getTipo());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}