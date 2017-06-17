package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
			stm1 = con.prepareStatement(
					  "SELECT d.name as name,d.surname as surname,a.d as date \n"
					+ "FROM appointments as a INNER JOIN patient as p ON p.username = a.pat_username \n"
					+ "INNER JOIN doctor as d ON a.doc_username = d.username \n"
					+ "WHERE p.username = ? AND a.d < CURRENT_DATE \n"
					+ "ORDER BY a.d");
			stm2= con.prepareStatement(
					  "SELECT * \n"
					+ "FROM ( \n"
					+ "	SELECT d.username, d.surname, d.name, \n"
					+ "	generate_series(a.d_start, a.d_end, interval '30 minutes') as avail_h \n"
					+ "	FROM doctor as d NATURAL JOIN availabillity as a \n"
					+ "	WHERE d.spec::varchar = ? AND a.d_start >= to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' ) \n"
					+ " 	AND a.d_end <= to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' ) \n"
					+ "	ORDER BY d.username,a.d_start) as r \n"
					+ "WHERE (r.username, r.avail_h) NOT IN \n"
					+ "( SELECT doc_username as username, d as avail_h from appointments); \n");
			stm3 = con.prepareStatement(
					  "SELECT d.name as name,d.surname as surname,a.d as date \n"
					+ "FROM appointments as a INNER JOIN patient as p ON p.username = a.pat_username \n"
					+ "INNER JOIN doctor as d ON a.doc_username = d.username \n"
					+ "WHERE p.username = ? AND a.d >= CURRENT_DATE \n"
					+ "ORDER BY a.d;");
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Appointment> getAppointmentHistory(Patient pat) {
		try {
			List<Appointment> aps = new ArrayList<>();
			stm1.setString(1, pat.getUsername());
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
	
	//NOTE check gia bugs
	public static List<Availability> getAvailableDoctors(String spec, String start, String end) {
		try {
			List<Availability> avs = new ArrayList<>();
			stm2.setString(1, spec);
			stm2.setString(2, start);
			stm2.setString(3, end);
			ResultSet rs = stm2.executeQuery();
			Doctor prev_doc  = new Doctor("","","");
			Availability prev_avail = new Availability(prev_doc, Timestamp.valueOf(start),  Timestamp.valueOf(end));
			while(rs.next()) {
				Doctor doc = new Doctor();
				doc.setUsername(rs.getString("username"));
				doc.setName(rs.getString("name"));
				doc.setSurname(rs.getString("surname"));
				if( !prev_doc.getUsername().equals(doc.getUsername()) ){
					avs.add(prev_avail);
					prev_avail = new Availability(doc,  Timestamp.valueOf(start),  Timestamp.valueOf(end));
					prev_doc = doc;
				}
				prev_avail.addHour(rs.getTimestamp("avail_h"));
			}
			if(avs.size()!=0){
				avs.add(prev_avail); //add teleutaio loop
				avs.remove(0); //remove 1o loop
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
			stm3.setString(1, pat.getUsername());
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
