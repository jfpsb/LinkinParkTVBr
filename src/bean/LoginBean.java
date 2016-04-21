package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import entidade.Admin;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {
	private String login;
	private String senha;
	private Admin admin = new Admin();
	
	public String entrar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if(login.equals("Felipe")) {
			if(senha.equals("12345")) {
				System.out.println(login + " --- " + senha);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Entrou", ""));
				return "home?faces-redirect=true";				
			}
		}
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não Entrou", ""));
		return "login?faces-redirect=true";
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
