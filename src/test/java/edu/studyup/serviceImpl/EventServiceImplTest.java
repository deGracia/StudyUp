package edu.studyup.serviceImpl;

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
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 3;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}

//	@name test The length of event name has to be less than(<=) 20 characters.
//	Case1: test the event name of more than 20 characters, exception excepted.
	@Test 
	void testLongEventName_badCase() {
		int eventID = 1; 
		String eventName = "TheseAreExact21chars."; 
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, eventName); 
		}); 
	}
	
//	Case2: test the event name of exact 20 characters, success excepted.
	@Test 
	void testLongestEventName_goodCase() throws StudyUpException {
		int eventID = 1;
		String eventName = "TheseAreExact20chars"; 
		eventServiceImpl.updateEventName(eventID, eventName);
		assertEquals(eventName, DataStorage.eventData.get(eventID).getName());
	}

//	Case3: test the event name of less than 20 random characters, success excepted.
	@Test 
	void testNormalEventName_goodCase() throws StudyUpException {
		int eventID = 1;
		String eventName = "LessThan20chars"; 
		eventServiceImpl.updateEventName(eventID, eventName);
		assertEquals(eventName, DataStorage.eventData.get(eventID).getName());
	}

//	@students There could at most be {@code 2 students} in an event.
//	Case 1: test the event 1 with two student, success excepted.
	@Test 
	void testTwoStudent_goodCase() throws StudyUpException {
		int eventID = 1;
		List<Student> eventStudents = new ArrayList<>();
		eventStudents = DataStorage.eventData.get(eventID).getStudents();
		// As there's already one student in event 1, create the second Student.
		Student student2 = new Student();
		student2.setFirstName("Shuyun");
		student2.setLastName("Yuan");
		student2.setEmail("yuanshuyun@email.com");
		student2.setId(2);
		// add the second student into event 1.
		eventServiceImpl.addStudentToEvent(student2, eventID);

		// Add Student2 to the expected eventStudents list.
		eventStudents.add(student2); 
		
		assertEquals(DataStorage.eventData.get(eventID).getStudents(), eventStudents);
		
//		Event event = DataStorage.eventData.get(1);
//		List<Student> students = event.getStudents();
//		assert(students.get(1).equals(student));
	}
	
//	Case 2: test the event with more than two student, excepted to be success.
	@Test 
	void testOneStudent_badCase() {
		int eventID = 1;
		// As there's already one student, create the second Student
		Student student2 = new Student();
		student2.setFirstName("Shuyun");
		student2.setLastName("Yuan");
		student2.setEmail("yuanshuyun@email.com");
		student2.setId(2);
		// Create the third Student
		Student student3 = new Student();
		student3.setFirstName("Zhengfeng");
		student3.setLastName("Lai");
		student3.setEmail("laizhengfeng@email.com");
		student3.setId(3);
		
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student2, eventID);
			eventServiceImpl.addStudentToEvent(student3, eventID);
		  });
	}
}
