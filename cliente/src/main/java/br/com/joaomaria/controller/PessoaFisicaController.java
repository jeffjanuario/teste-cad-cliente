package br.com.joaomaria.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joaomaria.model.PessoaFisicaModel;
import br.com.joaomaria.repository.PessoaFisicaRepository;
import br.com.joaomaria.utils.Utils;

@Named(value = "pessoaFisicaController")
@ViewScoped
public class PessoaFisicaController implements Serializable {

	private static final long serialVersionUID = 3075603547273812753L;

	@Inject
	transient PessoaFisicaModel pessoaFisicaModel;

	@Inject
	transient UsuarioController usuarioController;

	@Inject
	transient PessoaFisicaRepository pessoaRepository;

	public PessoaFisicaModel getPessoaFisicaModel() {
		return pessoaFisicaModel;
	}

	public void setPessoaFisicaModel(PessoaFisicaModel pessoaFisicaModel) {
		this.pessoaFisicaModel = pessoaFisicaModel;
	}

	/**
	 * SALVA UM NOVO REGISTRO VIA INPUT
	 */
	public void salvar() {
		pessoaFisicaModel.setUsuarioCadastro(this.usuarioController.GetUsuarioSession());
		pessoaRepository.salvar(this.pessoaFisicaModel);
		this.pessoaFisicaModel = null;
		Utils.MensagemInfo("Registro cadastrado com sucesso");
	}

}
