package br.com.joaomaria.repository.entity;

import java.io.Serializable;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "pessoa")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = -985546750310447172L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pessoa", updatable = false, nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_usuario_cadastro")
	private Usuario usuarioCadastro;

	@JoinColumn(name = "fl_tipo_pessoa")
	private String flTipoPessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getFlTipoPessoa() {
		return flTipoPessoa;
	}

	public void setFlTipoPessoa(String flTipoPessoa) {
		this.flTipoPessoa = flTipoPessoa;
	}

}
