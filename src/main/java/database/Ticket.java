package database;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int ticketId;

	@Column (name = "release_date")
	private LocalDate releaseDate;

	@Column (name = "obliterate_date")
	private LocalDate obliterateDate;

	@ManyToOne
	@JoinColumn (name = "dispenser_id")
	private Dispenser dispenser;

	public int getTicketId () {
		return this.ticketId;
	}

	public void setTicketId (int ticketId) {
		this.ticketId = ticketId;
	}

	public LocalDate getReleaseDate () {
		return this.releaseDate;
	}

	public void setReleaseDate (LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public LocalDate getObliterateDate () {
		return this.obliterateDate;
	}

	public void setObliterateDate (LocalDate obliterateDate) {
		this.obliterateDate = obliterateDate;
	}

	public Dispenser getDispenser () {
		return this.dispenser;
	}

	public void setDispenser (Dispenser dispenser) {
		this.dispenser = dispenser;
	}

	public Ticket () {
	}

	public Ticket (LocalDate releaseDate, Dispenser dispenser) {
		this.setReleaseDate(releaseDate);
		this.setDispenser(dispenser);
	}

	public Ticket (LocalDate releaseDate, LocalDate obliterateDate, Dispenser dispenser) {
		this.setReleaseDate(releaseDate);
		this.setObliterateDate(obliterateDate);
		this.setDispenser(dispenser);
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Ticket ticket)) return false;
		return this.getTicketId() == ticket.getTicketId() &&
			Objects.equals(this.getReleaseDate(), ticket.getReleaseDate()) &&
			Objects.equals(this.getObliterateDate(), ticket.getObliterateDate()) &&
			Objects.equals(this.getDispenser(), ticket.getDispenser());
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getTicketId(),
			this.getReleaseDate(),
			this.getObliterateDate(),
			this.getDispenser()
		);
	}

	@Override
	public String toString () {
		return "Ticket{" +
			"ticketId=" + this.getTicketId() +
			", releaseDate=" + this.getReleaseDate() +
			", obliterateDate=" + this.getObliterateDate() +
			", dispenser=" + this.getDispenser() +
			'}';
	}
}