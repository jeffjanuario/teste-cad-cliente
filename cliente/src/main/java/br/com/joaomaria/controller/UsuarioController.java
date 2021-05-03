package br.com.joaomaria.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
 
import org.apache.commons.lang3.StringUtils;

import br.com.joaomaria.model.UsuarioModel;
import br.com.joaomaria.repository.UsuarioRepository;
import br.com.joaomaria.repository.entity.Usuario;
import br.com.joaomaria.utils.Utils;

@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = -3146191743675379675L;

	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private Usuario usuarioEntity;

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	public UsuarioModel GetUsuarioSession() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		return (UsuarioModel) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	public String Logout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/index.xhtml?faces-redirect=true";
	}

	public String EfetuarLogin() {

		if (StringUtils.isEmpty(usuarioModel.getLogin()) || StringUtils.isBlank(usuarioModel.getLogin())) {

			Utils.Mensagem("Favor informar o login!");
			return null;
		} else if (StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())) {

			Utils.Mensagem("Favor informara senha!");
			return null;
		} else {

			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);

			if (usuarioEntity != null) {

				usuarioModel.setSenha(null);
				usuarioModel.setId(usuarioEntity.getId());

				FacesContext facesContext = FacesContext.getCurrentInstance();

				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

				return "sistema/home?faces-redirect=true";
			} else {

				Utils.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}

	}

}