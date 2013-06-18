package getbuzee.web;

import getbuzee.entity.Event;
import getbuzee.entity.Person;
import getbuzee.exception.CatchException;
import getbuzee.service.EventService;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import utils.Loggable;

@Named
//@RequestScoped TODO should be request scoped
@SessionScoped
@Loggable
@CatchException
public class EventManager  implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private EventService eventService;
    
    private Event currentEvent;
    
    private List<Event> allEvents;
    
    private List<Person> allParticipants;
    

    
    public List<Event> findAllEvents(){
    	this.allEvents = eventService.findAllEvents();
    	return allEvents;
    }
    

	public List<Event> getAllEvents() {
		this.allEvents = eventService.findAllEvents();
		return allEvents;
	}

	public void setAllEvents(List<Event> allEvents) {
		this.allEvents = allEvents;
	}
    
	public String createEvent(){
		eventService.createEvent(currentEvent);
		currentEvent=null;
		return "main";
	}
    
    public String updateEvent(Event event){
    	eventService.updateEvent(event);
    	return "main";
    }
    

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}
    
    

}
