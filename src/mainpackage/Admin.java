package mainpackage;

public class Admin extends User {

	public Admin(String username, String password) {
		super(username, password);
	}
	
	@Override
	public String toString() {
		return "Admin[ "+super.toString() + " ]";
	}

}
