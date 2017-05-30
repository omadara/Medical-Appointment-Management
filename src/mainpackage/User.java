package mainpackage;

public abstract class User {
	public static int usersCounter = 0;
	protected int id;
	protected String username;
	protected String password;
	protected String name;
	protected String surname;
	
	public User() { }
	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
		usersCounter++;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
