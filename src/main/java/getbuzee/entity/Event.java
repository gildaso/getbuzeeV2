package getbuzee.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="EVENT")
@NamedQueries({
@NamedQuery(name=Event.FIND_ALL_EVENTS, query="select e from Event e order by e.eventId"),
@NamedQuery(name=Event.FIND_BY_NAME, query="select e from Event e where e.name = :name order by e.eventId")
})
public class Event implements Serializable,Cloneable{
	@TableGenerator(name = "eventGen", table = "PERSISTENCE_EVENT_SEQUENCE_GENERATOR", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "EVENT_ID", allocationSize = 10)
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.TABLE, generator="eventGen")
	private Long eventId;
	private String name;
	private String type;
	private Date date;
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="PARTICIPATION",joinColumns=
		@JoinColumn(name="EVENT_ID",referencedColumnName="eventId"),
		inverseJoinColumns=
		@JoinColumn(name="PERSON_ID",referencedColumnName="personId"))
	private List<Event> participants = new ArrayList<Event>(0);
	
    public static final String FIND_BY_NAME = "Event.findByLogin";    
    public static final String FIND_ALL_EVENTS = "Event.findAll";
	
    public Event(){    	
    }
    

	/**
	 * @param eventId
	 * @param name
	 * @param type
	 * @param date
	 * @param description
	 * @param participants
	 */
	public Event(Long eventId, String name, String type, Date date,
			String description, List<Event> participants) {
		super();
		this.eventId = eventId;
		this.name = name;
		this.type = type;
		this.date = date;
		this.description = description;
		this.participants = participants;
	}


	

	/**
	 * @return the eventId
	 */
	public Long getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the participants
	 */
	public List<Event> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(List<Event> participants) {
		this.participants = participants;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}		
	
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
}
