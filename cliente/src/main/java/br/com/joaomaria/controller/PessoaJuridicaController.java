package br.com.joaomaria.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joaomaria.model.PessoaJuridicaModel;
import br.com.joaomaria.repository.PessoaJuridicaRepository;
import br.com.joaomaria.utils.Utils;

@Named(value = "pessoaJuridicaController")
@ViewScoped
public class PessoaJuridicaController implements Serializable {

	private static final long serialVersionUID = 7297102411555441300L;

	@Inject
	transient PessoaJuridicaModel pessoaJuridicaModel;

	@Inject
	transient UsuarioController usuarioController;

	@Inject
	transient PessoaJuridicaRepository pessoaJuridicaRepository;

	private String telefone;

	private boolean telefoneValido;

	private boolean showModal;

	public PessoaJuridicaModel getPessoaJuridicaModel() {
		return pessoaJuridicaModel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isTelefoneValido() {
		return telefoneValido;
	}

	public void setTelefoneValido(boolean telefoneValido) {
		this.telefoneValido = telefoneValido;
	}

	public boolean isShowModal() {
		return showModal;
	}

	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}

	public void onShowModal() {
		this.showModal = !this.showModal;
	}

	public void addTelefone() {
		if(this.telefone == null || this.telefone.isEmpty()) {
			this.telefoneValido = false;
			return;
		}
		if(this.pessoaJuridicaModel.getTelefones() == null) {
			this.pessoaJuridicaModel.setTelefones(new ArrayList<>());
		}
		if (this.telefone != null && this.pessoaJuridicaModel.getTelefones().contains(this.telefone)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone já adicionado",
					"Número: " + this.telefone);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			this.telefoneValido = false;
			this.telefone = null;
			return;
		}
		this.pessoaJuridicaModel.getTelefones().add(this.telefone);
		setTelefoneValido(true);
		this.telefone = null;
		this.showModal = false;
	}

	public void removerTelefone(String telefone) {
		this.pessoaJuridicaModel.getTelefones().remove(telefone);
	}

	/**
	 * SALVA UM NOVO REGISTRO VIA INPUT
	 */
	public void salvar() {
		if(pessoaJuridicaModel.getTelefones() == null  || pessoaJuridicaModel.getTelefones().isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Necessário ter pelo menos um telefone",
					"Campo: Telefones ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		pessoaJuridicaModel.setUsuarioCadastro(this.usuarioController.GetUsuarioSession());
		pessoaJuridicaRepository.salvar(this.pessoaJuridicaModel);
		this.pessoaJuridicaModel = new PessoaJuridicaModel();
		Utils.MensagemInfo("Registro cadastrado com sucesso");
	}

}
