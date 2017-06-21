package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import mainpackage.Admin;
import mainpackage.Doctor;
import mainpackage.Patient;
import mainpackage.User;

@WebListener
public class Accounts implements ServletContextListener{
	private static PreparedStatement stm1, stm2, stm3, stm4, stm5, stm6, stm7;
	private static Connection con;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			InitialContext context = new InitialContext();
			DataSource src = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
			con = src.getConnection();
			//statements
			stm1 = con.prepareStatement("SELECT * FROM patient WHERE username = ? AND password = ?");
			stm2 = con.prepareStatement("INSERT INTO PATIENT(amka,username,password,name,surname) VALUES( ?, ?, ?, ?, ?)");
			stm3 = con.prepareStatement("SELECT * FROM doctor WHERE username = ? AND password = ?");
			stm4 = con.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");
			stm5 = con.prepareStatement("INSERT INTO doctor(username,password,name,surname,spec) VALUES( ?, ?, ?, ?, ?::specialty)");
			stm6 = con.prepareStatement("DELETE FROM doctor WHERE username = ?");
			stm7 = con.prepareStatement("DELETE FROM patient WHERE username = ?");
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			stm1.close();stm2.close();stm3.close();stm4.close();stm5.close();stm6.close();stm7.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Patient getPatient(String username, String password) {
		try {
			stm1.setString(1, username);
			stm1.setString(2, password);
			ResultSet rs = stm1.executeQuery();
			if (rs.next()) {
				String amka = rs.getString("amka");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				return new Patient(username, password, amka, name, surname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Doctor getDoctor(String username, String password) {
		try {
			stm3.setString(1, username);
			stm3.setString(2, password);
			ResultSet rs = stm3.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String spec = rs.getString("spec");
				Doctor d = new Doctor(username, password, spec);
				d.setName(name);
				d.setSurname(surname);
				return d;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Admin getAdmin(String username, String password) {
		try {
			stm4.setString(1, username);
			stm4.setString(2, password);
			ResultSet rs = stm4.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				Admin a = new Admin(username, password);
				a.setName(name);
				a.setSurname(surname);
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean register(Patient pat) {
		try {
			stm2.setString(1, pat.getAmka());
			stm2.setString(2, pat.getUsername());
			stm2.setString(3, pat.getPassword());
			stm2.setString(4, pat.getName());
			stm2.setString(5, pat.getSurname());
			return stm2.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean register(Doctor doc) {
		try {
			stm5.setString(1, doc.getUsername());
			stm5.setString(2, doc.getPassword());
			stm5.setString(3, doc.getName());
			stm5.setString(4, doc.getSurname());
			stm5.setString(5, doc.getSpeciality());
			return stm5.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 public static boolean deleteDoc(String username){
		try{
			stm6.setString(1, username);
			return stm6.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	 public static boolean deletePatient(String username) {
		 try{
			 stm7.setString(1, username);
			 return stm7.executeUpdate() == 1;
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return false;
		 }
	 }

}
