package mainpackage;

import java.util.Date;

public class Appointment {
	private Doctor doctor;
	private Patient patient;
	private Date date;
	
	public Appointment(Doctor doctor, Patient patient) {
		this.doctor = doctor;
		this.patient = patient;
	}
	
	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Appointment[ "+doctor+", "+patient+", "+date+" ]";
	}

}
