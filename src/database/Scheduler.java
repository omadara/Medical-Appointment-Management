package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mainpackage.Appointment;
import mainpackage.Doctor;
import mainpackage.Patient;
import mainpackage.Doctor.DoctorSpec;

public class Scheduler {
	private static PreparedStatement stm1;
	private static Connection con;
	static{
		try {
			InitialContext context = new InitialContext();
			DataSource src = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
			con = src.getConnection();
			stm1 = con.prepareStatement("SELECT d.name as name,d.surname as surname,a.date_t as date\r\n"
					+ "FROM patient as p INNER JOIN appointments as a ON p.id = a.patient_id \r\n"
					+ "INNER JOIN doctor as d ON a.doctor_id = d.id\r\n"
					+ "WHERE p.id = ? AND a.date_t < CURRENT_DATE\r\n" + "ORDER BY a.date_t");			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Appointment> getAppointmentHistory(Patient pat) {
		try {
			List<Appointment> aps = new ArrayList<>();
			stm1.setInt(1, pat.getId());
			ResultSet rs = stm1.executeQuery();
			while(rs.next()) {
				Doctor doc = new Doctor();
				doc.setName(rs.getString("name"));
				doc.setSurname(rs.getString("surname"));
				Appointment ap = new Appointment(doc, pat);
				ap.setDate(rs.getDate("date"));
				aps.add(ap);
			}
			return aps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Appointment> getSchedule(Patient pat) {
		return null;
	}
	
	public List<Appointment> getSchedule(Doctor pat) {
		return null;
	}
	
	public List<Appointment> getAllAppointments() {
		return null;
	}
	
	public boolean makeAppointment(Patient pat, Doctor doc, Date date) {
		return false;
	}
	
	public boolean makeAppointment(Patient pat, DoctorSpec spec, Date date) {
		return false;
	}
	
	public boolean cancelAppointment(Appointment a) {
		return false;
	}
	
	public boolean setAvailable(Doctor doc, Date start, Date end) {
		return false;
	}
	
	public List<Appointment> search(Doctor doc) {
		return null;
	}
	
	public List<Appointment> search(DoctorSpec spec) {
		return null;
	}
	
}
