package br.com.joaomaria.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "usuario")
@Entity
@NamedQueries({
		@NamedQuery(name = "Usuario.findUsuario", query = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha ") })
public class Usuario implements Serializable {
	private static final long serialVersionUID = -2105628960122351612L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", updatable = false, nullable = false)
	private Long id;
	@Column(name = "login")
	private String login;

	@Column(name = "senha")
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
