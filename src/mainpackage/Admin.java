package mainpackage;

import java.util.List;

public class Admin extends User {

	public Admin(String username, String password) {
		super(username, password);
	}
	
	public boolean register(Doctor doc) {
		System.out.println("Registering doctor "+doc.getName()+" "+doc.getSurname());
		return true;
	}
	
	public boolean ban(User user) {
		System.out.println("Banning user "+user.getName()+" "+user.getSurname());
		return true;
	}
	
	public List<Appointment> getAllAppointments() {
		return null;
	}
	
	@Override
	public String toString() {
		return "Admin[ "+super.toString() + " ]";
	}

}
