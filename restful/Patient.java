package edu.cs157b.restful;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
public class Patient {
	private int id;
	private String firstname;
	private String lastname;
	private int age;
	private int myDoctor;
	private String medicalRecord;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public String getFirstName(){
		return firstname;
	}
	public void setFirstName(String firstname){
		this.firstname = firstname;
	}
	
	public String getLastName(){
		return lastname;
	}
	
	public void setLastName(String lastname){
		this.lastname = lastname;
	}
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
	}

	public int getMyDoctor() {
		return myDoctor;
	}

	public void setMyDoctor(int myDoctor) {
		this.myDoctor = myDoctor;
	}

	public String getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(String medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	
}
