package edu.cs157b.restful;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/patient")
public class PatientWeb {
	RestfulDAO test = new RestfulDAO();
	
	
	@Path("/allpatients")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public   List<Patient> getPatients() throws Exception{
		return test.findAllPatients();
	}
	
	@PUT @Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Patient updatePatient(Patient pat) throws Exception{
		test.updatePatient(pat);
		return pat;
	}
	@POST @Path("/addPatient")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Patient create(Patient pat) throws Exception{
		return test.createPatient(pat);
	}
	
	@Path("/getPatients/{myDoctor}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String bySpecialty(@PathParam("myDoctor") int myDoctor) throws Exception{
		return test.allPatientsofDoctor(myDoctor);
	}
}
