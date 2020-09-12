package com.concretepage;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

import com.concretepage.soap.EinStudent;
import com.concretepage.soap.StrukturInfo;

@Component
public class StudentUtility  {
	private final Logger log= Logger.getLogger(getClass().getName());
	private Map<Integer,EinStudent> studentMap = new HashMap<Integer,EinStudent>();
	public StudentUtility(){
		log.log(Level.INFO, "generieren StudentUtility.....");
		EinStudent s1 = new EinStudent();
		s1.setEgn(11111);
		s1.setStudentId(1);
		s1.getName().add("Iva");
		s1.getName().add("Abadjieva");
		s1.setAge(20);
		BigDecimal number = new BigDecimal("43.5");
		s1.setClazz(number);
		s1.setMymodell("1");
		StrukturInfo sti= new StrukturInfo();
		sti.setStufe("Info1");
		
		GregorianCalendar calender = new GregorianCalendar(1952, Calendar.JULY, 15);
		calender.setTimeZone( TimeZone.getTimeZone("CET") );
		XMLGregorianCalendar gr;
		try{
		 gr = DatatypeFactory.newInstance().newXMLGregorianCalendar(calender);
		}catch(Exception e){
			gr=null;
		}
		sti.setStufeDatum(gr);
		s1.getInfo().add(sti);
		StrukturInfo sti1= new StrukturInfo();
		sti1.setStufe("Info11");
		s1.getInfo().add(sti1);
		studentMap.put(1, s1);
		
		EinStudent s2 = new EinStudent();
		s2.setEgn(22222);
		s2.setStudentId(2);
		s2.getName().add("Olga");
		s2.setAge(22);
		BigDecimal number2 = new BigDecimal("37.8");
		s2.setClazz(number2);
		s2.setMymodell("111111");
		sti1.setStufe("Info22");
		s2.getInfo().add(sti1);
		
		studentMap.put(2, s2);
	}
	
	@PostConstruct
    public void initData() {
        // initialize countries map
    }
	
	public EinStudent getStudent(int studentId){
		return studentMap.get(studentId);
	}
}
