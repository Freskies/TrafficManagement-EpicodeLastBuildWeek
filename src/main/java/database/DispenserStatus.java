package database;

public enum DispenserStatus {
	ACTIVE, INACTIVE;

	@Override
	public String toString () {
		return switch (this) {
			case ACTIVE -> "Active";
			case INACTIVE -> "Inactive";
		};
	}
}
