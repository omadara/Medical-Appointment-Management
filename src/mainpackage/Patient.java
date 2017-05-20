package mainpackage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mainpackage.Doctor.DoctorSpec;

public class Patient extends User {
	private int amka;
	
	public Patient(String username, String password, int amka) {
		super(username, password);
		this.amka = amka;
	}

	public boolean register() {
		return true;
	}
	
	public List<Appointment> search(Doctor doc) {
		return null;
	}
	
	public List<Appointment> search(DoctorSpec spec) {
		System.out.println("Searching for "+spec);
		return null;
	}
	
	public List<Appointment> getSchedule() {
		System.out.println("Getting schedule for patient!");
		return null;
	}
	
	public List<Appointment> getAppointmentHistory() {
		return null;
	}
	
	public void setAppointment(Doctor doc, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		System.out.println("Made appointment with "+doc.getName()+" "+doc.getSurname()+" at: "+sdf.format(date));
	}
	
	public void setAppointment(DoctorSpec spec, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm"); 
		System.out.println("Made appointment with "+spec+" at: "+sdf.format(date));
	}
	
	public boolean cancel(Appointment a) {
		return true;
	}

	public int getAmka() {
		return amka;
	}
	
	@Override
	public String toString() {
		return "Patient[ "+super.toString() + ", AMKA: "+amka+" ]";
	}

}
