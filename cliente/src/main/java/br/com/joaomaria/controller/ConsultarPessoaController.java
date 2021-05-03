package br.com.joaomaria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joaomaria.model.PessoaFisicaModel;
import br.com.joaomaria.model.PessoaJuridicaModel;
import br.com.joaomaria.model.PessoaModel;
import br.com.joaomaria.model.TipoPessoaEnum;
import br.com.joaomaria.repository.PessoaFisicaRepository;
import br.com.joaomaria.repository.PessoaJuridicaRepository;
import br.com.joaomaria.utils.Utils;

@Named(value = "consultarPessoaController")
@ViewScoped
public class ConsultarPessoaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	transient private PessoaModel pessoaModel;

	@Inject
	transient private PessoaFisicaModel pessoaFisicaModel;

	@Inject
	transient private PessoaJuridicaModel pessoaJuridicaModel;

	@Produces
	private List<PessoaModel> pessoas;

	@Produces
	List<PessoaFisicaModel> pessoasFisicas;

	@Produces
	List<PessoaJuridicaModel> pessoasJuridicas;

	@Produces
	private List<TipoPessoaEnum> tiposPessoa;

	private TipoPessoaEnum tipoPessoaSelected;

	private boolean pessoaFisica = false;

	private String telefone;

	private boolean telefoneValido;

	private boolean showModal;
	
	private String desc;

	@Inject
	transient private PessoaJuridicaRepository pessoaJuridicaRepository;
	@Inject
	transient private PessoaFisicaRepository pessoaFisicaRepository;

	public String getDescricaoTipoPessoa(String tipo) {
		if(TipoPessoaEnum.FISICA.getTipo().equals(tipo)) {
			return TipoPessoaEnum.FISICA.getDescricao();
		}
		return TipoPessoaEnum.JURIDICA.getDescricao();
	}
	
	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}

	public List<PessoaFisicaModel> getPessoasFisicas() {
		return pessoasFisicas;
	}

	public void setPessoasFisicas(List<PessoaFisicaModel> pessoasFisicas) {
		this.pessoasFisicas = pessoasFisicas;
	}

	public List<PessoaJuridicaModel> getPessoasJuridicas() {
		return pessoasJuridicas;
	}

	public void setPessoasJuridicas(List<PessoaJuridicaModel> pessoasJuridicas) {
		this.pessoasJuridicas = pessoasJuridicas;
	}

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	public PessoaFisicaModel getPessoaFisicaModel() {
		return pessoaFisicaModel;
	}

	public void setPessoaFisicaModel(PessoaFisicaModel pessoaFisicaModel) {
		this.pessoaFisicaModel = pessoaFisicaModel;
	}

	public PessoaJuridicaModel getPessoaJuridicaModel() {
		return pessoaJuridicaModel;
	}

	public void setPessoaJuridicaModel(PessoaJuridicaModel pessoaJuridicaModel) {
		this.pessoaJuridicaModel = pessoaJuridicaModel;
	}

	/***
	 * CARREGA AS PESSOAS NA INICIALIZAÇÃO
	 */
	@PostConstruct
	public void init() {
		// RETORNAR AS PESSOAS CADASTRADAS
		tiposPessoa = Arrays.asList(TipoPessoaEnum.values());
		pessoas = new ArrayList<>();
		this.pessoasFisicas = pessoaFisicaRepository.getPessoasFisicas(this.desc);
		this.pessoasJuridicas = pessoaJuridicaRepository.getPessoasJuridicas(this.desc);

		List<PessoaModel> fisicaModel = pessoasFisicas.stream().map(pessoaFisica -> {
			PessoaModel pessoa = new PessoaModel();
			pessoa.setId(pessoaFisica.getId());
			pessoa.setFlTipoPessoa(TipoPessoaEnum.FISICA.getTipo());
			pessoa.setNome(pessoaFisica.getNome());
			pessoa.setDocumento(pessoaFisica.getCpf());
			return pessoa;
		}).collect(Collectors.toList());

		List<PessoaModel> juridicaModel = pessoasJuridicas.stream().map(pessoaJuridica -> {
			PessoaModel pessoa = new PessoaModel();
			pessoa.setId(pessoaJuridica.getId());
			pessoa.setFlTipoPessoa(TipoPessoaEnum.JURIDICA.getTipo());
			pessoa.setNome(pessoaJuridica.getRazao());
			pessoa.setDocumento(pessoaJuridica.getCnpj());
			return pessoa;
		}).collect(Collectors.toList());
		this.pessoas.addAll(fisicaModel);
		this.pessoas.addAll(juridicaModel);
	}

	/***
	 * CARREGA INFORMAÇÕES DE UMA PESSOA PARA SER EDITADA
	 * 
	 * @param pessoaModel
	 */
	public void Editar(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
		if (pessoaModel.getFlTipoPessoa().equals(TipoPessoaEnum.FISICA.getTipo())) {
			setTipoPessoaSelected(TipoPessoaEnum.FISICA);
			this.pessoaFisicaModel = this.pessoasFisicas.stream()
					.filter(pessoaFisica -> pessoaFisica.getId().equals(this.pessoaModel.getId())).findFirst().get();
		} else {
			setTipoPessoaSelected(TipoPessoaEnum.JURIDICA);
			this.pessoaJuridicaModel = this.pessoasJuridicas.stream()
					.filter(pessoaJuridica -> pessoaJuridica.getId().equals(this.pessoaModel.getId())).findFirst()
					.get();
		}
		this.habilitar();
	}

	/***
	 * EXCLUINDO UM REGISTRO
	 * 
	 * @param pessoaModel
	 */
	public void ExcluirPessoa(PessoaModel pessoaModel) {
		if (pessoaModel.getFlTipoPessoa().equals(TipoPessoaEnum.FISICA.getTipo())) {
			// EXCLUI A PESSOA DO BANCO DE DADOS
			this.pessoaFisicaRepository.excluir(pessoaModel.getId());
			this.pessoasFisicas.remove(this.pessoaFisicaModel);
		} else {
			this.pessoaJuridicaRepository.excluir(pessoaModel.getId());
			this.pessoasJuridicas.remove(this.pessoaJuridicaModel);
		}
		// REMOVENDO A PESSOA DA LISTA
		// ASSIM QUE É A PESSOA É REMOVIDA DA LISTA O DATATABLE É ATUALIZADO
		this.pessoas.remove(pessoaModel);
		Utils.MensagemInfo("Registro removido com sucesso");
	}

	/***
	 * ATUALIZA O REGISTRO QUE FOI ALTERADO
	 */
	public void alterar() {

		if (pessoaModel.getFlTipoPessoa().equals(TipoPessoaEnum.FISICA.getTipo())) {
			this.pessoaFisicaRepository.alterar(this.pessoaFisicaModel);
		} else {
			this.pessoaJuridicaRepository.alterar(this.pessoaJuridicaModel);
		}
		Utils.MensagemInfo("Registro alterado com sucesso");
		/* RECARREGA OS REGISTROS */
		this.init();
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

	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void addTelefone() {
		if (this.telefone == null || this.telefone.isEmpty()) {
			this.telefoneValido = false;
			return;
		}
		if (this.telefone != null && this.pessoaJuridicaModel.getTelefones().contains(this.telefone)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone j� adicionado",
					"N�mero: " + this.telefone);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			this.telefoneValido = false;
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

}