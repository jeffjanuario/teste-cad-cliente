package br.com.joaomaria.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.joaomaria.model.UsuarioModel;
import br.com.joaomaria.repository.entity.Usuario;
import br.com.joaomaria.utils.Utils;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	public Usuario ValidaUsuario(UsuarioModel usuarioModel) {
		try {
			Query query = Utils.JpaEntityManager().createNamedQuery("Usuario.findUsuario");
			query.setParameter("login", usuarioModel.getLogin());
			query.setParameter("senha", usuarioModel.getSenha());

			return (Usuario) query.getSingleResult();

		} catch (Exception e) {

			return null;
		}
	}
}