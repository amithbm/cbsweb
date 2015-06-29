package com.cbsweb.rest.data;

import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement*/
public class Vendor {
	
	public Vendor() {
		
	}

	public Vendor(String name, String email, String phno, String pwd) {
		super();
		this.name = name;
		this.email = email;
		this.phno = phno;
		this.pwd = pwd;
	}

	public String name;
	public String email;
	public String phno;
	public String pwd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhno() {
		return phno;
	}
	public void setPhno(String phno) {
		this.phno = phno;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "Vendor [name=" + name + ", email=" + email + ", phno=" + phno
				+ ", pwd=" + pwd + "]";
	}	
	
}
