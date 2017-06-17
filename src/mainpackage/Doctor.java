package mainpackage;

public class Doctor extends User {
	private String speciality;
	
	public Doctor() { }
	public Doctor(String username, String password, String spciality) {
		super(username, password);
		this.speciality = spciality;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	@Override
	public String toString() {
		return "Doctor[ "+super.toString() + ", Speciality: "+speciality+" ]";
	}

}
