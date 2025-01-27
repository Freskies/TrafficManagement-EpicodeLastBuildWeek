package database;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "cards")
public class Card {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int cardId;

	@Column (name = "owner_full_name")
	private String ownerFullName;

	@Column (name = "release_date")
	private LocalDate releaseDate;

	@ManyToOne
	@JoinColumn (name = "dispenser_id")
	private Dispenser dispenser;

	public int getCardId () {
		return this.cardId;
	}

	public void setCardId (int cardId) {
		this.cardId = cardId;
	}

	public String getOwnerFullName () {
		return this.ownerFullName;
	}

	public void setOwnerFullName (String ownerFullName) {
		this.ownerFullName = ownerFullName;
	}

	public LocalDate getReleaseDate () {
		return this.releaseDate;
	}

	public void setReleaseDate (LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Dispenser getDispenser () {
		return this.dispenser;
	}

	public void setDispenser (Dispenser dispenser) {
		this.dispenser = dispenser;
	}

	public Card () {
	}

	public Card (String owner, LocalDate date, Dispenser dispenser) {
		this.setOwnerFullName(owner);
		this.setReleaseDate(date);
		this.setDispenser(dispenser);
	}

	public boolean isActive () {
		return this.getReleaseDate().isAfter(LocalDate.now().minusYears(1));
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Card card)) return false;
		return this.getCardId() == card.getCardId() &&
			Objects.equals(this.getOwnerFullName(), card.getOwnerFullName()) &&
			Objects.equals(this.getReleaseDate(), card.getReleaseDate());
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getCardId(),
			this.getOwnerFullName(),
			this.getReleaseDate()
		);
	}

	@Override
	public String toString () {
		return "Card{" +
			"cardId=" + this.getCardId() +
			", ownerFullName='" + this.getOwnerFullName() + '\'' +
			", releaseDate=" + this.getReleaseDate() +
			'}';
	}
}
