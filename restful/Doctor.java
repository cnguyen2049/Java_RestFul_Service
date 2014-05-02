package edu.cs157b.restful;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class Doctor {
		private int id;

	    private String firstname;

	    private String lastname;

	    private String specialty;
	    public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstname;
		}

		public void setFirstName(String firstname) {
			this.firstname = firstname;
		}

		public String getLastName() {
			return lastname;
		}
		public void setLastName(String lastname){
			this.lastname = lastname;
		}
		public String getSpecialty(){
			return specialty;
		}
		public void setSpecialty(String specialty){
			this.specialty = specialty;
		}

		
}

