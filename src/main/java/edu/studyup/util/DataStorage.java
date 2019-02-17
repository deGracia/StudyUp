package edu.studyup.util;

import java.util.HashMap;
import java.util.Map;

import edu.studyup.entity.Event;

/***
 * 
 * This class is a temporary class to be used in place of a database. The static
 * variable eventList holds all the event data.
 * 
 * @author Shivani
 * 
 */

// fix the vulnerable field bug, by making it final
// But should not wrapped into Collections.unmodifiableSet/List/Map/etc, otherwise this class cannot be suitably used
// thus I regard it as a false positive bug 
public class DataStorage {
	public static final Map<Integer, Event> eventData = new HashMap<Integer, Event>();
}
