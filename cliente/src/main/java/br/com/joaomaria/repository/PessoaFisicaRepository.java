package br.com.joaomaria.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.joaomaria.model.PessoaFisicaModel;
import br.com.joaomaria.model.UsuarioModel;
import br.com.joaomaria.repository.entity.PessoaFisica;
import br.com.joaomaria.repository.entity.Usuario;
import br.com.joaomaria.utils.UtilString;
import br.com.joaomaria.utils.Utils;

public class PessoaFisicaRepository implements Serializable {

	private static final long serialVersionUID = -6817908707429625791L;

	@Inject
	PessoaFisica pessoaFisica;

	EntityManager entityManager;

	/***
	 * MÉTODO RESPONSÁVEL POR SALVAR UMA NOVA PESSOA FÍSICA
	 * 
	 * @param pessoa
	 */
	public void salvar(PessoaFisicaModel pessoa) {
		entityManager = Utils.JpaEntityManager();
		pessoaFisica = new PessoaFisica();
		pessoaFisica.setNome(pessoa.getNome());
		pessoaFisica.setCpf(UtilString.removeMascara(pessoa.getCpf()));
		pessoaFisica.setDtNascimento(pessoa.getDtNascimento());
		pessoaFisica.setTelefone(UtilString.removeMascara(pessoa.getTelefone()));
		pessoaFisica.setEmail(pessoa.getEmail());
		Usuario usuarioCadastro = entityManager.find(Usuario.class, pessoa.getUsuarioCadastro().getId());
		pessoaFisica.setUsuarioCadastro(usuarioCadastro);
		entityManager.persist(pessoaFisica);
	}

	public List<PessoaFisicaModel> getPessoasFisicas(String desc) {
		entityManager = Utils.JpaEntityManager();
		
		StringBuilder sb = new StringBuilder("SELECT p From PessoaFisica p");
		if(desc != null) {
			sb.append(" WHERE LOWER(p.nome) LIKE LOWER(:desc) OR LOWER(p.cpf) LIKE LOWER(:desc)");
		}
		Query query = entityManager.createQuery(sb.toString());
		if(desc != null) {
			query.setParameter("desc", "%" + desc + "%");
		}

		@SuppressWarnings("unchecked")
		Collection<PessoaFisica> pessoasEntity = (Collection<PessoaFisica>) query.getResultList();

		return pessoasEntity.stream().map(pessoa -> {
			PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
			pessoaFisicaModel.setId(pessoa.getId());
			pessoaFisicaModel.setNome(pessoa.getNome());
			pessoaFisicaModel.setCpf(pessoa.getCpf());
			pessoaFisicaModel.setDtNascimento(pessoa.getDtNascimento());
			pessoaFisicaModel.setTelefone(pessoa.getTelefone());
			pessoaFisicaModel.setEmail(pessoa.getEmail());
			Usuario usuarioEntity = pessoa.getUsuarioCadastro();
			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setId(usuarioEntity.getId());
			pessoaFisicaModel.setUsuarioCadastro(usuarioModel);
			return pessoaFisicaModel;
		}).collect(Collectors.toList());
	}

	/***
	 * CONSULTA UMA PESSOA CADASTRADA PELO CÃ“DIGO
	 * 
	 * @param codigo
	 * @return
	 */
	private PessoaFisica getPessoa(Long id) {
		entityManager = Utils.JpaEntityManager();
		return entityManager.find(PessoaFisica.class, id);
	}

	/***
	 * EXCLUI UM REGISTRO DO BANCO DE DADOS
	 * 
	 * @param long
	 */
	public void excluir(Long codigo) {

		entityManager = Utils.JpaEntityManager();

		PessoaFisica pessoaEntity = this.getPessoa(codigo);

		entityManager.remove(pessoaEntity);
	}

	public void alterar(PessoaFisicaModel pessoaModel) {

		entityManager = Utils.JpaEntityManager();

		PessoaFisica pessoaEntity = this.getPessoa(pessoaModel.getId());

		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setCpf(UtilString.removeMascara(pessoaModel.getCpf()));
		pessoaEntity.setDtNascimento(pessoaModel.getDtNascimento());
		pessoaEntity.setTelefone(UtilString.removeMascara(pessoaModel.getTelefone()));
		pessoaEntity.setEmail(pessoaModel.getEmail());

		entityManager.merge(pessoaEntity);
	}
}