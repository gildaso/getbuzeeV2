package getbuzee.service;

import getbuzee.entity.Event;
import getbuzee.entity.Person;
import getbuzee.exception.ValidationException;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import utils.Loggable;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@Loggable
public class EventService implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private EntityManager em;
    
    private List<Event> allEvents;

    // ======================================
    // =              Public Methods        =
    // ======================================


    public Event createEvent(final Event Event) {

        if (Event == null)
            throw new ValidationException("Event object is null");

        em.persist(Event);

        return Event;
    }

    public Event findEventByName(final String name) {

        if (name == null)
            throw new ValidationException("Invalid name");
        em.flush();
        TypedQuery<Event> typedQuery = em.createNamedQuery(Event.FIND_BY_NAME,Event.class);
        typedQuery.setParameter("name", name);

        try {
            Event event = (Event)typedQuery.getSingleResult();
            em.refresh(event);
            return event;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Event> findAllEvents() {
        TypedQuery<Event> typedQuery = em.createNamedQuery(Event.FIND_ALL_EVENTS, Event.class);
        this.allEvents = typedQuery.getResultList();
        return allEvents;
    }

    public Event updateEvent(Event event) {

        // Make sure the object is valid
        if (event == null)
            throw new ValidationException("Event object is null");
        // Update the object in the database        
        event = em.merge(event);       

        return event;
    }
    
    public void removeParticipant(final Person person,final Event event){
    	Query q = em.createNativeQuery("delete from participation where person_id= ? and event_id= ? ");
    	q.setParameter(1, person.getPersonId());
    	q.setParameter(2, event.getEventId());
    	q.executeUpdate();
    }

    public void removeEvent(final Event event) {
        if (event == null)
            throw new ValidationException("Event object is null");

        em.remove(em.merge(event));
    }    
    
    
    public void dropTableEvent(){
    	em.createNativeQuery("delete from participation").executeUpdate();
    	em.createNativeQuery("delete from event").executeUpdate();
    	em.flush();
    }

	public List<Event> getAllEvents() {
		return allEvents;
	}

	public void setAllEvents(List<Event> allEvents) {
		this.allEvents = allEvents;
	}
    
    
}
