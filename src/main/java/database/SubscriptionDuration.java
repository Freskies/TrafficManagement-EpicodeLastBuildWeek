package database;

public enum SubscriptionDuration {
	WEEKLY, MONTHLY;

	@Override
	public String toString () {
		return switch (this) {
			case WEEKLY -> "Weekly";
			case MONTHLY -> "Monthly";
		};
	}
}
