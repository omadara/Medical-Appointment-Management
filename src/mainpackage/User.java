package mainpackage;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class User {
	public static int usersCounter = 0;
	protected static Connection con;
	static{
		try {
			InitialContext context = new InitialContext();
			DataSource source = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
			con = source.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected int id;
	protected String username;
	protected String password;
	protected String name;
	protected String surname;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		usersCounter++;
	}
	
	public boolean login() {
		System.out.println("Login...");
		return false;
	}
	
	public void logout() {
		System.out.println("Logout...");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public String toString() {
		return "username: "+username
				+", password: "+password
				+", name: "+name
				+", surname: "+surname;
	}
	
}
