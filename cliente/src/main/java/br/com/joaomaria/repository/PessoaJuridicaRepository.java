package br.com.joaomaria.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.joaomaria.model.PessoaJuridicaModel;
import br.com.joaomaria.repository.entity.PessoaJuridica;
import br.com.joaomaria.repository.entity.Usuario;
import br.com.joaomaria.utils.UtilConvert;
import br.com.joaomaria.utils.UtilString;
import br.com.joaomaria.utils.Utils;

public class PessoaJuridicaRepository implements Serializable {

	private static final long serialVersionUID = -6817908707429625791L;

	@Inject
	PessoaJuridica pessoaJuridica;

	EntityManager entityManager;

	/***
	 * MÉTODO RESPONSÁVEL POR SALVAR UMA NOVA PESSOA JURÍDICA
	 * 
	 * @param pessoaJuridicaModel
	 */
	public void salvar(PessoaJuridicaModel pessoaJuridicaModel) {
		entityManager = Utils.JpaEntityManager();
		pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setRazao(pessoaJuridicaModel.getRazao());
		pessoaJuridica.setFantasia(pessoaJuridicaModel.getFantasia());
		pessoaJuridica.setCnpj(UtilString.removeMascara(pessoaJuridicaModel.getCnpj()));

		List<String> telefones = pessoaJuridicaModel.getTelefones().stream().map(telefone -> {
			return UtilString.removeMascara(telefone);
		}).collect(Collectors.toList());

		pessoaJuridica.setTelefones(telefones);
		pessoaJuridica.setSite(pessoaJuridicaModel.getSite());
		Usuario usuarioCadastro = entityManager.find(Usuario.class, pessoaJuridicaModel.getUsuarioCadastro().getId());
		pessoaJuridica.setUsuarioCadastro(usuarioCadastro);
		entityManager.persist(pessoaJuridica);
	}

	public List<PessoaJuridicaModel> getPessoasJuridicas(String desc) {
		entityManager = Utils.JpaEntityManager();

		StringBuilder sb = new StringBuilder("SELECT distinct p From PessoaJuridica p JOIN FETCH p.telefones");
		if (desc != null) {
			sb.append(" WHERE LOWER(p.razao) LIKE LOWER(:desc) OR LOWER(p.fantasia) LIKE LOWER(:desc) OR LOWER(p.cnpj) LIKE (:desc) ");
		}
		Query query = entityManager.createQuery(sb.toString());
		if (desc != null) {
			query.setParameter("desc", "%" + desc + "%");
		}

		@SuppressWarnings("unchecked")
		Collection<PessoaJuridica> pessoasEntity = (Collection<PessoaJuridica>) query.getResultList();

		return pessoasEntity.stream().map(pessoa -> {
			PessoaJuridicaModel pessoaJuridicaModel = new PessoaJuridicaModel();
			pessoaJuridicaModel.setId(pessoa.getId());
			pessoaJuridicaModel.setRazao(pessoa.getRazao());
			pessoaJuridicaModel.setFantasia(pessoa.getFantasia());

			List<String> telefonesModel = pessoa.getTelefones().stream()
					.map(telefone -> UtilConvert.formatTelefone(telefone)).collect(Collectors.toList());

			pessoaJuridicaModel.setTelefones(telefonesModel);
			pessoaJuridicaModel.setCnpj(pessoa.getCnpj());
			pessoaJuridicaModel.setSite(pessoa.getSite());
			return pessoaJuridicaModel;
		}).collect(Collectors.toList());
	}

	/***
	 * CONSULTA UMA PESSOA CADASTRADA PELO CÃ“DIGO
	 * 
	 * @param codigo
	 * @return
	 */
	private PessoaJuridica getPessoa(Long id) {
		entityManager = Utils.JpaEntityManager();
		return entityManager.find(PessoaJuridica.class, id);
	}

	/***
	 * EXCLUI UM REGISTRO DO BANCO DE DADOS
	 * 
	 * @param long
	 */
	public void excluir(Long codigo) {

		entityManager = Utils.JpaEntityManager();

		PessoaJuridica pessoaEntity = this.getPessoa(codigo);

		entityManager.remove(pessoaEntity);
	}

	public void alterar(PessoaJuridicaModel pessoaModel) {

		entityManager = Utils.JpaEntityManager();

		PessoaJuridica pessoaEntity = this.getPessoa(pessoaModel.getId());

		pessoaEntity.setRazao(pessoaModel.getRazao());
		pessoaEntity.setFantasia(pessoaModel.getFantasia());
		pessoaEntity.setCnpj(UtilString.removeMascara(pessoaModel.getCnpj()));

		List<String> telefones = pessoaModel.getTelefones().stream().map(telefone -> {
			return UtilString.removeMascara(telefone);
		}).collect(Collectors.toList());

		pessoaEntity.setTelefones(telefones);
		pessoaEntity.setSite(pessoaModel.getSite());

		entityManager.merge(pessoaEntity);
	}
}