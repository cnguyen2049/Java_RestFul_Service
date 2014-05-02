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
@Path("/doctor")
public class DoctorWeb {
	RestfulDAO test = new RestfulDAO();
	@Path("/htmlinfo")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTMLHospitalInfo(){
		return "<html> <h4> Triple HHH</h4></html>";
	}
	
	@Path("/alldoctors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public   List<Doctor> getDoctors() throws Exception{
		return test.findAllDoctors();
	}
	
	@Path("/alldoctor")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public   String getDoctor() throws Exception{
		return test.allDoctor();
	}
	@Path("/searchspecialty/{specialty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String bySpecialty(@PathParam("specialty") String specialty) throws Exception{
		return test.search(specialty);
	}
	
	@PUT @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Doctor updateDoctor(Doctor doc) throws Exception{
		test.updateDoctor(doc);
		return doc;
	}
	@DELETE @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public void remove(@PathParam("id") int id) throws Exception {
		test.remove(id);
	}
	
	@POST @Path("/addDoctor")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Doctor create(Doctor doc) throws Exception{
		return test.create(doc);
	}
	
}
