package mainpackage;

public abstract class User {
	public static int usersCounter = 0;
	private String username;
	private String password;
	private String name;
	private String surname;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		usersCounter++;
	}
	
	public boolean login() {
		System.out.println("Login...");
		return true;
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
