package edu.cs157b.restful;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.jdo.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;

import org.codehaus.jettison.json.JSONArray;

import edu.cs157b.util.*;

public class RestfulDAO {
	
	/*
	 * This Method output everything in String The other one does it in JSON
	 */
	public String allDoctor() throws Exception{
		PreparedStatement query = null;
		Connection c = null;
		String returnString = "";
		
		try{
			c = DatabaseConnection.getDataSource().getConnection();
			query = c.prepareStatement("select * from doctor");
			ResultSet rs = query.executeQuery();
			while(rs.next()){
				int id = rs.getInt("D_ID");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				String specialty = rs.getString("specialty");
				returnString += id + " " + firstName + " " + lastName + " "+ specialty + "\n";
			}
			query.close();
		}
		catch(SQLException e){
			e.printStackTrace();
            throw new RuntimeException(e);
		}
		finally{
			if(c!=null) c.close();
		}
		return returnString ;
	}
	public String allPatientsofDoctor(int id) throws Exception{
		PreparedStatement query = null;
		Connection c = null;
		String returnString ="";
		String sql = "select * from patient WHERE myDoctor =" + id;
		
		try{
			c = DatabaseConnection.getDataSource().getConnection();
			query = c.prepareStatement(sql);
			//query.setInt(1,id);
			ResultSet rs = query.executeQuery();
			while(rs.next()){
				int pid = rs.getInt("P_ID");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				int age = rs.getInt("age");
				String medicalRecord = rs.getString("MedicalRecord");
				returnString += pid + " " + firstName + " " + lastName + " " + age + " " + medicalRecord +"\n";
			}
			query.close();
		}
		catch(SQLException e){
			e.printStackTrace();
            throw new RuntimeException(e);
		}
		finally{
			if(c!=null) c.close();
		}
		return returnString;
	}
	/*
	 * Return GET as JSON
	 */
	public List<Doctor> findAllDoctors() throws Exception 
	{
		List<Doctor> list = new ArrayList<>();
		Connection c = null;
		String sql = "SELECT * FROM doctor";
		String returnString;
		
		try 
		{
			c = DatabaseConnection.getDataSource().getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) 
			{
				list.add(processRow(rs));
			}
			
			ResultSet rs1 = s.executeQuery(sql);
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs1);
			
			returnString = json.toString();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		finally 
		{
			if (c!=null) c.close();
		}
		//return returnString;
		return list;
	}
	public List<Patient> findAllPatients() throws Exception 
	{
		List<Patient> list = new ArrayList<>();
		Connection c = null;
		String sql = "SELECT * FROM patient";
		String returnString;
		
		try 
		{
			c = DatabaseConnection.getDataSource().getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) 
			{
				list.add(processRow2(rs));
			}
			
			ResultSet rs1 = s.executeQuery(sql);
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs1);
			
			returnString = json.toString();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		finally 
		{
			if (c!=null) c.close();
		}
		//return returnString;
		return list;
	}
	
	public Doctor updateDoctor(Doctor doc) throws Exception{
		PreparedStatement query = null;
		Connection c = null;
		
		
		try{
			c = DatabaseConnection.getDataSource().getConnection();
			query = c.prepareStatement("UPDATE doctor SET specialty=? WHERE D_ID =?");
			//query.setString(1,doc.getFirstName());
			//query.setString(2,doc.getLastName());
			query.setString(1, doc.getSpecialty());
			query.setInt(2,doc.getId());
			query.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
            throw new RuntimeException(e);
		}
		finally{
			if(c!=null) c.close();
		}
		return doc;
	}
	
	public Patient updatePatient(Patient pat) throws Exception{
		PreparedStatement query = null;
		Connection c = null;
		try{
			c = DatabaseConnection.getDataSource().getConnection();
			query = c.prepareStatement("UPDATE PATIENT SET age=?,myDoctor=?,MedicalRecord=? WHERE P_ID =?");
			//query.setString(1,pat.getFirstName());
			//query.setString(2,pat.getLastName());
			query.setInt(1,pat.getAge());
			query.setInt(2,pat.getMyDoctor());
			query.setString(3, pat.getMedicalRecord());
			query.setInt(4, pat.getId());
			query.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
            throw new RuntimeException(e);
		}
		finally{
			if(c!=null) c.close();
		}
		return pat;
	}
	
	public String search(String s) throws Exception{
		PreparedStatement query = null;
		Connection c = null;
		String returnString ="";
		String sql = "SELECT * FROM doctor as e " +
				"WHERE UPPER(specialty) LIKE ? " +	
				"ORDER BY specialty";
		try{
			c = DatabaseConnection.getDataSource().getConnection();
			query = c.prepareStatement(sql);
			query.setString(1, "%" + s.toUpperCase() + "%");
			ResultSet rs = query.executeQuery();
			while(rs.next()){
				int id = rs.getInt("D_ID");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				String specialty = rs.getString("specialty");
				returnString += id + " " + firstName + " " + lastName + "\n";
			}
		}catch(SQLException e){
			e.printStackTrace();
            throw new RuntimeException(e);
		}finally{
			if(c!=null) c.close();
		}
		return returnString;
	}
	
	 protected Doctor processRow(ResultSet rs) throws SQLException {
	        Doctor doc = new Doctor();
	        doc.setId(rs.getInt("D_ID"));
	        doc.setFirstName(rs.getString("firstname"));
	        doc.setLastName(rs.getString("lastname"));
	        doc.setSpecialty(rs.getString("specialty"));
	        return doc;
	    }
	 protected Patient processRow2(ResultSet rs) throws SQLException {
	        Patient pat = new Patient();
	        pat.setId(rs.getInt("P_ID"));
	        pat.setFirstName(rs.getString("firstname"));
	        pat.setLastName(rs.getString("lastname"));
	        pat.setAge(rs.getInt("age"));
	        pat.setMyDoctor(rs.getInt("myDoctor"));
	        pat.setMedicalRecord(rs.getString("MedicalRecord"));
	        return pat;
	    }
	 
	 public boolean remove(int id) throws Exception {
	        Connection c = null;
	        try {
	        	c = DatabaseConnection.getDataSource().getConnection();
	            PreparedStatement ps = c.prepareStatement("DELETE FROM Doctor WHERE D_ID=?");
	            ps.setInt(1, id);
	            int count = ps.executeUpdate();
	            return count == 1;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				if(c!=null) c.close();
			}
	    }
	 public Doctor create(Doctor doc) throws Exception{
	        Connection c = null;
	        PreparedStatement ps = null;
	        try {
	        	c = DatabaseConnection.getDataSource().getConnection();
	            ps = c.prepareStatement("INSERT INTO Doctor (D_ID,firstName,lastName,Specialty) VALUES (?,?, ?, ?)");
	            ps.setInt(1, doc.getId());
	            ps.setString(2, doc.getFirstName());
	            ps.setString(3, doc.getLastName());
	            ps.setString(4, doc.getSpecialty());
	            ps.executeUpdate();
	            //ResultSet rs = ps.getGeneratedKeys();
	            //rs.next();
	            // Update the id in the returned object. This is important as this value must be returned to the client.
	            //int id = rs.getInt(1);
	            //doc.setId(id);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				if(c!=null) c.close();
			}
	        return doc;
	    }
	 
	 public Patient createPatient(Patient pat) throws Exception{
	        Connection c = null;
	        PreparedStatement ps = null;
	        try {
	        	c = DatabaseConnection.getDataSource().getConnection();
	            ps = c.prepareStatement("INSERT INTO Patient (P_ID,firstName,lastName,age,myDoctor,MedicalRecord) VALUES (?,?, ?,?,?,?)");
	            ps.setInt(1, pat.getId());
	            ps.setString(2, pat.getFirstName());
	            ps.setString(3, pat.getLastName());
	            ps.setInt(4, pat.getAge());
	            ps.setInt(5,pat.getMyDoctor());
	            ps.setString(6,pat.getMedicalRecord());
	            ps.executeUpdate();
	            //ResultSet rs = ps.getGeneratedKeys();
	            //rs.next();
	            // Update the id in the returned object. This is important as this value must be returned to the client.
	            //int id = rs.getInt(1);
	            //doc.setId(id);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				if(c!=null) c.close();
			}
	        return pat;
	    }
	 
}
