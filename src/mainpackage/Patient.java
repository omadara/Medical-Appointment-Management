package mainpackage;

public class Patient extends User {
	private String amka;
	
	public Patient(int id, String username, String password, String amka, String name, String surname) {
		super(id, username, password);
		this.amka = amka;
		this.name = name;
		this.surname = surname;
	}
	
	public void setAmka(String amka) {
		this.amka = amka;
	}

	public String getAmka() {
		return amka;
	}
	
	@Override
	public String toString() {
		return "Patient[ "+super.toString() + ", AMKA: "+amka+" ]";
	}

}
