package actions;

import server.communications.Server;

import com.opensymphony.xwork2.ActionSupport;

public class Identificar extends ActionSupport {
	private String email, passwd;
	
	@Override
	public String execute() throws Exception {
		try {
			Server s = Server.get();
			s.add(email, passwd);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
