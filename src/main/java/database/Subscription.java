package database;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int subscriptionId;

	@ManyToOne
	@JoinColumn (name = "card_id")
	private Card card;

	@ManyToOne
	@JoinColumn (name = "dispenser_id")
	private Dispenser dispenser;

	@Column (name = "release_date")
	private LocalDate releaseDate;

	@Enumerated (EnumType.STRING)
	@Column (name = "duration")
	private SubscriptionDuration duration;

	public int getSubscriptionId () {
		return subscriptionId;
	}

	public void setSubscriptionId (int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Card getCard () {
		return this.card;
	}

	public void setCard (Card card) {
		this.card = card;
	}

	public Dispenser getDispenser () {
		return this.dispenser;
	}

	public void setDispenser (Dispenser dispenser) {
		this.dispenser = dispenser;
	}

	public LocalDate getReleaseDate () {
		return this.releaseDate;
	}

	public void setReleaseDate (LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public SubscriptionDuration getDuration () {
		return this.duration;
	}

	public void setDuration (SubscriptionDuration duration) {
		this.duration = duration;
	}

	public Subscription () {
	}

	public Subscription (Card card, Dispenser dispenser, LocalDate releaseDate, SubscriptionDuration duration) {
		this.setCard(card);
		this.setDispenser(dispenser);
		this.setReleaseDate(releaseDate);
		this.setDuration(duration);
	}

	public boolean isActive() {
		int days = switch (this.getDuration()) {
			case SubscriptionDuration.WEEKLY -> 7;
			case SubscriptionDuration.MONTHLY -> 30;
		};
		return LocalDate.now().isBefore(this.getReleaseDate().plusDays(days));
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Subscription that)) return false;
		return this.getSubscriptionId() == that.getSubscriptionId() &&
			Objects.equals(this.getCard(), that.getCard()) &&
			Objects.equals(this.getDispenser(), that.getDispenser()) &&
			Objects.equals(this.getReleaseDate(), that.getReleaseDate()) &&
			this.getDuration() == that.getDuration();
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getSubscriptionId(),
			this.getCard(),
			this.getDispenser(),
			this.getReleaseDate(),
			this.getDuration()
		);
	}

	@Override
	public String toString () {
		return "Subscription{" +
			"subscriptionId=" + this.getSubscriptionId() +
			", card=" + this.getCard() +
			", dispenser=" + this.getDispenser() +
			", releaseDate=" + this.getReleaseDate() +
			", duration=" + this.getDuration() +
			'}';
	}
}