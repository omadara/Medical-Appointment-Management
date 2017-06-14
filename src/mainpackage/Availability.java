package mainpackage;

import java.util.Date;

public class Availability {
	private Doctor doctor;
	private Date start;
	private Date end;
	
	public Availability(Doctor doctor, Date start, Date end) {
		this.doctor = doctor;
		this.start = start;
		this.end = end;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
