package br.com.joaomaria.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joaomaria.model.TipoPessoaEnum;
import br.com.joaomaria.repository.PessoaFisicaRepository;

@Named(value = "pessoaController")
@ViewScoped
public class PessoaController implements Serializable {

	private static final long serialVersionUID = 106576291011827864L;
	@Produces
	private List<TipoPessoaEnum> tiposPessoa;
	private TipoPessoaEnum tipoPessoaSelected;
	private boolean pessoaFisica = false;

	@Inject
	transient UsuarioController usuarioController;

	@Inject
	transient PessoaFisicaRepository pessoaRepository;

	@PostConstruct
	public void init() {
		tiposPessoa = Arrays.asList(TipoPessoaEnum.values());
	}

	public List<TipoPessoaEnum> getTiposPessoa() {
		return tiposPessoa;
	}

	public TipoPessoaEnum getTipoPessoaSelected() {
		return tipoPessoaSelected;
	}

	public void setTipoPessoaSelected(TipoPessoaEnum tipoPessoaSelected) {
		this.tipoPessoaSelected = tipoPessoaSelected;
	}

	public boolean isPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(boolean pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public void habilitar() {
		this.setPessoaFisica(tipoPessoaSelected.equals(TipoPessoaEnum.FISICA));
	}
}
