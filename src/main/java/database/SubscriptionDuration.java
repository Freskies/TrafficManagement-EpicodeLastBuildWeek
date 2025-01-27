package database;

public enum SubscriptionDuration {
	MONTHLY, YEARLY;

	@Override
	public String toString () {
		return switch (this) {
			case MONTHLY -> "Monthly";
			case YEARLY -> "Yearly";
		};
	}
}
