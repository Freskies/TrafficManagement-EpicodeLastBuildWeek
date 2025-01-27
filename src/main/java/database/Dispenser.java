package database;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "dispensers")
public class Dispenser {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int dispenserId;

	@Column (name = "location")
	private String location;

	@Enumerated (EnumType.STRING)
	@Column (name = "status")
	private DispenserStatus status;

	@OneToMany (mappedBy = "dispenser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ticket> tickets;

	@OneToMany (mappedBy = "dispenser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Subscription> subscriptions;

	public int getDispenserId () {
		return this.dispenserId;
	}

	public void setDispenserId (int dispenserId) {
		this.dispenserId = dispenserId;
	}

	public String getLocation () {
		return this.location;
	}

	public void setLocation (String location) {
		this.location = location;
	}

	public DispenserStatus getStatus () {
		return this.status;
	}

	public void setStatus (DispenserStatus status) {
		this.status = status;
	}

	public List<Ticket> getTickets () {
		return this.tickets;
	}

	public void setTickets (List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Subscription> getSubscriptions () {
		return this.subscriptions;
	}

	public void setSubscriptions (List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Dispenser () {
	}

	public Dispenser (String location, DispenserStatus status) {
		this.setLocation(location);
		this.setStatus(status);
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Dispenser dispenser)) return false;
		return this.getDispenserId() == dispenser.getDispenserId() &&
			Objects.equals(this.getLocation(), dispenser.getLocation()) &&
			this.getStatus() == dispenser.getStatus();
	}

	@Override
	public int hashCode () {
		return Objects.hash(this.getDispenserId(), this.getLocation(), this.getStatus());
	}

	@Override
	public String toString () {
		return "Dispenser{" +
			"dispenserId=" + this.getDispenserId() +
			", location='" + this.getLocation() + '\'' +
			", status=" + this.getStatus() +
			'}';
	}
}
