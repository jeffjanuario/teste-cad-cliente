package br.com.joaomaria.model;

import java.util.Date;

public class PessoaFisicaModel {

	private Long id;
	private String nome;
	private String cpf;
	private Date dtNascimento;
	private String telefone;
	private String email;
	private UsuarioModel usuarioCadastro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public UsuarioModel getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(UsuarioModel usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

}