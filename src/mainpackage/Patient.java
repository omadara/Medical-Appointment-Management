package mainpackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mainpackage.Doctor.DoctorSpec;

public class Patient extends User {
	private static PreparedStatement stm1, stm2;
	static{
		try {
			stm1 = con.prepareStatement("SELECT * FROM patient WHERE username = ? AND password = ?");
			stm2 = con.prepareStatement("SELECT d.name as name,d.surname as surname,a.date_t as date\r\n" + 
					"FROM patient as p INNER JOIN appointments as a ON p.id = a.patient_id \r\n" + 
					"INNER JOIN doctor as d ON a.doctor_id = d.id\r\n" + 
					"WHERE p.id = ? AND a.date_t < CURRENT_DATE\r\n" + 
					"ORDER BY a.date_t");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private String amka;
	
	public Patient(String username, String password) {
		super(username, password);
	}
	
	@Override
	public boolean login() {
		try {
			stm1.setString(1, username);
			stm1.setString(2, password);
			ResultSet rs = stm1.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
				amka = rs.getString("amka");
				name = rs.getString("name");
				surname = rs.getString("surname");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet getAppointmentHistory() {
		try {
			stm2.setInt(1, id);
			ResultSet rs = stm2.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
