package edu.studyup.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.serviceImpl.EventServiceImpl;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;
public class EventTest {
	EventServiceImpl eventServiceImpl;
	//get a list of students in an event, assert whether it is null
	@Test
	void getListOfStudents() throws StudyUpException{
		int eventID = 1;
		List<Student> students_now = DataStorage.eventData.get(eventID).getStudents();
		assertNotNull(students_now);
	}
	
	//delete students based on ID, assert whether this student is null
	@Test
	void deleteEvent() throws StudyUpException{
		int eventID = 1;
		Event event = eventServiceImpl.deleteEvent(eventID);
		assertNull(DataStorage.eventData.get(eventID));
	}
	
	//test: add a student to an empty event
	@Test
	void AddStudentToEmptyEvent() throws StudyUpException{
		int eventID = 2;
		Event event = new Event();
		event.setEventID(1);
		event.setName("Event 1");
		event.setDate(new Date());
		Location location = new Location(-100,50);
		event.setLocation(location);
		
		Student student2 = new Student();
		student2.setFirstName("Roger");
		student2.setLastName("Lai");
		student2.setEmail("JeffLai@gmail.com");
		student2.setId(2);
		
		DataStorage.eventData.put(eventID, event);
		List<Student> students = new ArrayList<>();
		students.add(student2);
		
		Assertions.assertEquals(students, eventServiceImpl.addStudentToEvent(student2, eventID).getStudents());
	}
	
	//get an event date and assert whether it is not null
	@Test
	void getDateOfEvent() throws StudyUpException{
		int eventID = 1;
		assertNotNull(DataStorage.eventData.get(eventID).getDate());
	}
	
	//get an event Location and assert it is not null
	@Test
	void getLocationOfEvent() throws StudyUpException{
		int eventID = 1;
		assertNotNull(DataStorage.eventData.get(eventID).getLocation());
	}
	
	//get a list of active events, assert it is not null
	@Test
	void getActiveEvents() throws StudyUpException{
		List<Event> ActiveEvents = eventServiceImpl.getActiveEvents();
		assertNotNull(ActiveEvents);
	}
	
	//test the mixture dates of past and future events
	//@SuppressWarnings("deprecation")
	/*
	 * @Test void testMixtureDate() { Event futureEvent = new Event();
	 * futureEvent.setEventID(2); futureEvent.setDate(new Date(2050,1,1));
	 * futureEvent.setName("Event 2"); Location location = new Location(-100,50);
	 * futureEvent.setLocation(location);
	 * 
	 * DataStorage.eventData.put(futureEvent.getEventID(), futureEvent); List<Event>
	 * eventList = new ArrayList<>(); eventList.add(futureEvent);
	 * 
	 * Assertions.assertEquals(eventList,eventServiceImpl.getActiveEvents());
	 * 
	 * 
	 * }
	 */	
}
