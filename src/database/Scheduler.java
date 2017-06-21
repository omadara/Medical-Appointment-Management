package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
	private static PreparedStatement stm1, stm2, stm3, stm4, stm5, stm6, stm7, stm8, stm9, stm10;
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
					+ "WHERE p.username = ? AND a.d < CURRENT_TIMESTAMP "
					+ "ORDER BY a.d");
			stm2= con.prepareStatement(
					  "SELECT * "
					+ "FROM ( "
					+ "	SELECT d.username, d.surname, d.name, "
					+ "		generate_series(a.d_start, a.d_end, interval '30 minutes') as avail_h "
					+ "	FROM doctor as d NATURAL JOIN availabillity as a "
					+ "	WHERE d.spec::varchar = ? "
					+ "	ORDER BY d.username,a.d_start) as r "
					+ "WHERE (r.username, r.avail_h) NOT IN "
					+ "( SELECT doc_username as username, d as avail_h FROM appointments) "
					+ "AND avail_h >= CURRENT_TIMESTAMP  AND avail_h >= to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' ) "
					+ "AND avail_h < to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS' );");
			stm3 = con.prepareStatement(
					  "SELECT d.username as username, d.name as name,d.surname as surname,a.d as date "
					+ "FROM appointments as a INNER JOIN patient as p ON p.username = a.pat_username "
					+ "INNER JOIN doctor as d ON a.doc_username = d.username "
					+ "WHERE p.username = ? AND a.d >= CURRENT_TIMESTAMP "
					+ "ORDER BY a.d;");
			stm4 = con.prepareStatement(
					  "SELECT p.username as username, p.name as name,p.surname as surname,a.d as date "
					+ "FROM appointments as a INNER JOIN patient as p on p.username = a.pat_username "
					+ "INNER JOIN doctor as d on a.doc_username = d.username "
					+ "WHERE d.username = ? AND a.d >= CURRENT_TIMESTAMP "
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

			stm7 = con.prepareStatement(
					  "SELECT * "
					+ "FROM doctor as d NATURAL JOIN availabillity as av "
					+ "	INNER JOIN appointments as ap ON ap.doc_username=d.username "
					+ "WHERE d.username= ? AND ( "
					+ "		(ap.d BETWEEN ?::timestamp AND ?::timestamp) "
					+ "		OR (av.d_start BETWEEN ?::timestamp AND ?::timestamp) "
					+ "		OR (av.d_end BETWEEN ?::timestamp AND ?::timestamp ) "
					+ "		OR (av.d_start <= ?::timestamp AND ?::timestamp <= av.d_end) "
					+ "	) LIMIT 1;");


			stm8 = con.prepareStatement("INSERT INTO availabillity VALUES( ? , ?::timestamp , ?::timestamp );");

			stm9 = con.prepareStatement("SELECT d_start as start, d_end as end FROM availabillity WHERE username = ? AND d_end >= CURRENT_TIMESTAMP ORDER BY start; ");

			stm10 = con.prepareStatement(
					  "DELETE FROM appointments "
					+ "WHERE doc_username = ? AND pat_username = ? AND d = ?::timestamp "
					+ "AND d >= CURRENT_TIMESTAMP + interval '3' day ;");
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			stm1.close();stm2.close();stm3.close();stm4.close();stm5.close();stm6.close();stm7.close();stm8.close();stm9.close();stm10.close();
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
				doc.setUsername(rs.getString("username"));
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
				pat.setUsername(rs.getString("username"));
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

	public static boolean setAvailable(Doctor doc, String start, String end) {
		try {
			stm7.setString(1, doc.getUsername());
			stm7.setString(2, start);
			stm7.setString(3, end);
			stm7.setString(4, start);
			stm7.setString(5, end);
			stm7.setString(6, start);
			stm7.setString(7, end);
			stm7.setString(8, start);
			stm7.setString(9, end);
			//an yparxei estw mia tetoia pleiada tote to diasthma (start,end) den einai egkyro
			if(stm7.executeQuery().isBeforeFirst()) return false;
			stm8.setString(1, doc.getUsername());
			stm8.setString(2, start);
			stm8.setString(3, end);
			return stm8.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Availability> getAvailableTimeSpans(Doctor doc) {
		List<Availability> avs = new ArrayList<>();
		try {
			stm9.setString(1, doc.getUsername());
			ResultSet rs = stm9.executeQuery();
			while(rs.next()) {
				avs.add(new Availability(doc, rs.getTimestamp("start"), rs.getTimestamp("end")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avs;
	}

	public static boolean cancelAppointment(String doctorUsername, String patientUsername, String date) {
		try{
			stm10.setString(1, doctorUsername);
			stm10.setString(2, patientUsername);
			stm10.setString(3, date);
			return stm10.executeUpdate() != 0;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public static boolean cancelAppointment(String doctorUsername, Patient pat, String date) {
		return cancelAppointment(doctorUsername, pat.getUsername(), date);
	}

	public static boolean cancelAppointment(Doctor doc, String patientUsername, String date) {
		return cancelAppointment(doc.getUsername(), patientUsername, date);
	}

}
