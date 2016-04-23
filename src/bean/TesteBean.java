package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import hibernateconnection.HibernateUtil;

@ManagedBean(name = "testeBean")
@RequestScoped
public class TesteBean {
	private String nome;

	public void con() {
		Session s = null;
		try {			
			s = HibernateUtil.getSessionFactory().openSession();
			
			System.out.println("AEHOOO");
			
			nome= "OLAR";
		}
		catch (Exception e) {
			System.out.println("ERRROU");
			nome= "NAO OLAR";
			e.printStackTrace();
		}
		finally {
			s.close();
		}
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
