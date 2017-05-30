package mainpackage;

public class Doctor extends User {
	public enum DoctorSpec {
		Pathologos, Ofthalmiatros, Orthopedikos
	}
	
	private DoctorSpec speciality;
		
	public Doctor() { }
	public Doctor(int id, String username, String password, DoctorSpec spciality) {
		super(id, username, password);
		this.speciality = spciality;
	}

	public DoctorSpec getSpeciality() {
		return speciality;
	}

	public void setSpeciality(DoctorSpec speciality) {
		this.speciality = speciality;
	}
	
	@Override
	public String toString() {
		return "Doctor[ "+super.toString() + ", Speciality: "+speciality+" ]";
	}

}
