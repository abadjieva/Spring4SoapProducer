package com.concretepage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xml.sax.SAXException;

import com.concretepage.model.Partnerfirma;
import com.concretepage.model.StatusEnum;
import com.concretepage.service.EmployeeService;
import com.concretepage.soap.EinDatensatz;
import com.concretepage.soap.GetDatenRequest;
import com.concretepage.soap.GetDatenResponse;
import com.concretepage.soap.GetStudentRequest;
import com.concretepage.soap.GetStudentResponse;
import com.concretepage.StudentUtility;

// http://localhost:8080/spring4soap-1/soapws/students.wsdl
@Endpoint
public class StudentEndpoint {
	private static final String NAMESPACE_URI = "http://concretepage.com/soap";

	private final Logger log = Logger.getLogger(getClass().getName());
	

	@Resource
	private StudentUtility studentUtility;

	@Autowired
	@Qualifier("employeeService")
	private EmployeeService service;
	
	UserDetails userdetails;
	
	@Autowired
	ApplicationContext applicationContext;
		
	@Autowired
	public AuthenticationManagerBuilder auth;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDatenRequest")
	@ResponsePayload
	public GetDatenResponse getDaten(
			@RequestPayload GetDatenRequest request) {
	
		GetDatenResponse response = new GetDatenResponse();
		int id=request.getDatenId();
		EinDatensatz satz= new EinDatensatz();
		satz.setSatzId(id);
		List<String> liste= new ArrayList<String>();
		liste.add("Record");
		liste.add("H�user");
		satz.getElement().addAll(liste);
		response.setDaten(satz);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentRequest")
	@ResponsePayload
	public GetStudentResponse getStudents(
			@RequestPayload GetStudentRequest request) {

		AuthenticationManager authenticationManager = (AuthenticationManager)auth.getObject();
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("dba", "root123");
		Authentication                      auth  = authenticationManager.authenticate(token);
		//Object obj= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		log.log(Level.INFO, "------ "+((UserDetails)auth.getPrincipal()).getUsername());
		log.log(Level.INFO, "------ "+((UserDetails)auth.getPrincipal()).getPassword());
		//log.log(Level.INFO, "------ "+userdetails.getUsername()+ " "+ auth.getCredentials().toString());
		
		GetStudentResponse response = new GetStudentResponse();

		try {
			List<StatusEnum> liste = service.findAlle();
			Integer id;

			try {
				id = request.getStudentId();
				log.log(Level.INFO, id.toString()); // damit zm Exception kommt
			} catch (Exception e1) {
				
				id = request.getStudentId1();
			}

			log.log(Level.INFO, id.toString());

			int i1 = id.intValue();
			if (i1>2) {
				log.log(Level.INFO, "i1>2 "+i1);
				response.setTechnischerResponseText("Bei ID>2 gibt es keine Informationen...");
			} else {
				response.setStud(studentUtility.getStudent(id));
				
				ExpBean eb = applicationContext.getBean(ExpBean.class);
				log.log(Level.INFO, "ExpBean aus dem Context "+eb.getTemp());

			}

			log.log(Level.INFO, "size = " + liste.size());

			JAXBContext jaxbContext = JAXBContext
					.newInstance(GetStudentResponse.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File("Response.xml");
			jaxbMarshaller.marshal(response, file);
			// jaxbMarshaller.marshal(customer, System.out);

			// ///DB-ZUGRIFF
			try {
				service.setzenStatusStop();
			} catch (RuntimeException e) {
				log.log(Level.SEVERE, "aus der DB Runtime: " + e.getCause());
				response.setTechnischerResponseText("FEHLER2="+e.getCause());
			} catch (Exception e1) {
				log.log(Level.SEVERE,
						"aus der DB Exception: " + e1.getMessage());
				response.setTechnischerResponseText("FEHLER3="+e1.getMessage());
			}
			// ///


			// --------

		} catch (Exception e) {
			log.log(Level.SEVERE, "FEHLER="+e.getMessage());

			response.setTechnischerResponseText("FEHLER1="+e.getMessage());

		}

		//erstelleXML(response);
		return response;
	}

	private void erstelleXML(GetStudentResponse response) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(GetStudentResponse.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			marshaller.marshal(response, stream);

			//FileOutputStream fos = null;
			try(FileOutputStream fos = new FileOutputStream(new File("output.xml")))
			{
				// im Tomcat/bin
				//fos = new FileOutputStream(new File("output.xml"));
				stream.writeTo(fos);
			} catch (IOException ioe) {
				// Handle exception here
				ioe.printStackTrace();
			} 
//			finally {
//				fos.close();
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
