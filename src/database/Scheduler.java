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
import mainpackage.Availability;
import mainpackage.Doctor;
import mainpackage.Patient;

public class Scheduler {
	private static PreparedStatement stm1, stm2, stm3;
	private static Connection con;
	static{
		initialize();
	}

	private static void initialize() {
		try {
			InitialContext context = new InitialContext();
			DataSource src = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
			con = src.getConnection();
			//statements
			stm1 = con.prepareStatement("SELECT d.name as name,d.surname as surname,a.date_t as date\r\n"
					+ "FROM patient as p INNER JOIN appointments as a ON p.id = a.patient_id \r\n"
					+ "INNER JOIN doctor as d ON a.doctor_id = d.id\r\n"
					+ "WHERE p.id = ? AND a.date_t < CURRENT_DATE\r\n" + "ORDER BY a.date_t");	
			stm2 = con.prepareStatement("SELECT d.surname,d.name,a.start_t as start,a.end_t as end\r\n" + 
					"FROM doctor as d INNER JOIN availables as a ON d.id=a.doctor_id\r\n" + 
					"WHERE d.spec = ? AND end_t > current_date\r\n" + 
					"ORDER BY d.surname,d.name;");
			stm3 = con.prepareStatement("SELECT d.name as name,d.surname as surname,a.date_t as date\r\n" + 
					"FROM patient as p INNER JOIN appointments as a ON p.id = a.patient_id INNER JOIN doctor as d ON a.doctor_id = d.id\r\n" + 
					"WHERE p.id = ? AND a.date_t >= CURRENT_DATE\r\n" + 
					"ORDER BY a.date_t;");
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

	public static List<Availability> getAvailableDoctors(String spec) {
		try {
			List<Availability> avs = new ArrayList<>();
			stm2.setString(1, spec.toLowerCase());
			ResultSet rs = stm2.executeQuery();
			while(rs.next()) {
				Doctor doc = new Doctor();
				doc.setName(rs.getString("name"));
				doc.setSurname(rs.getString("surname"));
				Date start = rs.getDate("start");
				Date end = rs.getDate("end");
				avs.add(new Availability(doc, start, end));
			}
			return avs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Appointment> getSchedule(Patient pat) {
		try {
			List<Appointment> aps = new ArrayList<>();
			stm3.setInt(1, pat.getId());
			ResultSet rs = stm3.executeQuery();
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
	
	public static List<Appointment> getSchedule(Doctor pat) {
		return null;
	}
	
	public static List<Appointment> getAllAppointments() {
		return null;
	}
	
	public static boolean makeAppointment(Patient pat, Doctor doc, Date date) {
		return false;
	}
	
	public static boolean makeAppointment(Patient pat, String spec, Date date) {
		return false;
	}
	
	public static boolean cancelAppointment(Appointment a) {
		return false;
	}
	
	public static boolean setAvailable(Doctor doc, Date start, Date end) {
		return false;
	}
	
	public static List<Appointment> search(Doctor doc) {
		return null;
	}
	
	public static List<Appointment> search(String spec) {
		return null;
	}
	
}
