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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import mainpackage.Appointment;
import mainpackage.Availability;
import mainpackage.Doctor;
import mainpackage.Patient;

@WebListener
public class Scheduler implements ServletContextListener{
	private static PreparedStatement stm1, stm2, stm3, stm4, stm5, stm6;
	private static Connection con;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			InitialContext context = new InitialContext();
			DataSource src = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
			con = src.getConnection();
			//statements
			stm1 = con.prepareStatement(
					  "SELECT d.name as name,d.surname as surname,a.d as date "
					+ "FROM appointments as a INNER JOIN patient as p ON p.username = a.pat_username "
					+ "INNER JOIN doctor as d ON a.doc_username = d.username "
					+ "WHERE p.username = ? AND a.d < CURRENT_DATE "
					+ "ORDER BY a.d");
			stm2= con.prepareStatement(
					  "SELECT * "
					+ "FROM ( "
					+ "	SELECT d.username, d.surname, d.name, "
					+ "		generate_series(a.d_start, a.d_end, interval '30 minutes') as avail_h "
					+ "	FROM doctor as d NATURAL JOIN availabillity as a "
					+ "	WHERE d.spec::varchar = ? AND a.d_start >= to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' ) "
					+ " 	AND a.d_end <= to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' ) "
					+ "	ORDER BY d.username,a.d_start) as r "
					+ "WHERE (r.username, r.avail_h) NOT IN "
					+ "( SELECT doc_username as username, d as avail_h FROM appointments);");
			stm3 = con.prepareStatement(
					  "SELECT d.name as name,d.surname as surname,a.d as date "
					+ "FROM appointments as a INNER JOIN patient as p ON p.username = a.pat_username "
					+ "INNER JOIN doctor as d ON a.doc_username = d.username "
					+ "WHERE p.username = ? AND a.d >= CURRENT_DATE "
					+ "ORDER BY a.d;");
			stm4 = con.prepareStatement("SELECT p.name as name,p.surname as surname,a.d as date "
					+ "FROM appointments as a INNER JOIN patient as p on p.username = a.pat_username "
					+ "INNER JOIN doctor as d on a.doc_username = d.username "
					+ "WHERE d.username = ? AND a.d >= CURRENT_DATE "
					+ "ORDER BY a.d;");

			stm5 = con.prepareStatement(
					  "SELECT * "
					+ "FROM ( "
					+ "	SELECT d.username, d.surname, d.name, "
					+ "		generate_series(a.d_start, a.d_end, interval '30 minutes') as avail_h "
					+ "	FROM doctor as d NATURAL JOIN availabillity as a "
					+ "	WHERE username = ? "
					+ "	ORDER BY d.username,a.d_start) as r "
					+ "WHERE (r.username, r.avail_h) NOT IN "
					+ "( SELECT doc_username as username, d as avail_h FROM appointments ) "
					+ "AND avail_h = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' );");

			stm6 = con.prepareStatement(
					  "INSERT INTO appointments (doc_username, pat_username, d) "
					+ "VALUES (?, ?, to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' ));");

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			stm1.close();stm2.close();stm3.close();stm4.close();stm5.close();stm6.close();
			con.close();
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
				ap.setDate(rs.getTimestamp("date"));
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
				ap.setDate(rs.getTimestamp("date"));
				aps.add(ap);
			}
			return aps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Appointment> getSchedule(Doctor doc) {
		try {
			List<Appointment> aps = new ArrayList<>();
			stm4.setString(1, doc.getUsername());
			ResultSet rs = stm4.executeQuery();
			while(rs.next()) {
				Patient pat = new Patient();
				pat.setName(rs.getString("name"));
				pat.setSurname(rs.getString("surname"));
				Appointment ap = new Appointment(doc, pat);
				ap.setDate(rs.getTimestamp("date"));
				aps.add(ap);
			}
			return aps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static boolean isDoctorAvailable(String doctorUserName, String date){
		try{
			stm5.setString(1, doctorUserName);
			stm5.setString(2, date);
			ResultSet rs = stm5.executeQuery();
			return rs.isBeforeFirst(); //true an rs oxi keno
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isDoctorAvailable(Doctor doc, String date){
		return isDoctorAvailable(doc.getUsername(), date);
	}

	public static boolean scheduleAppointment(Patient pat, String doc, String date) {
		try{
			stm6.setString(1, doc);
			stm6.setString(2, pat.getUsername());
			stm6.setString(3, date);
			return stm6.executeUpdate() != 0;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public static boolean scheduleAppointment(Patient pat, Doctor doc, String date) {
		return scheduleAppointment(pat, doc.getUsername(), date);
	}

	public static boolean cancelAppointment(Appointment a) {
		return false;
	}

	public static List<Appointment> getAllAppointments() {
		return null;
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
