package mainpackage;

import java.util.Date;
import java.util.List;

public class Doctor extends User {
	public enum DoctorSpec {
		Pathologos, Ofthalmiatros, Orthopedikos
	}
	
	private DoctorSpec speciality;
	
	public Doctor(String username, String password, DoctorSpec spciality) {
		super(username, password);
		this.speciality = spciality;
	}
	
	public void availableAt(Date start, Date end) {
		System.out.println("Setting doctor's availability..");
	}
	
	public List<Appointment> getSchedule() {
		System.out.println("Getting schedule for doctor!");
		return null;
	}
	
	public boolean cancel(Appointment a) {
		return true;
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
