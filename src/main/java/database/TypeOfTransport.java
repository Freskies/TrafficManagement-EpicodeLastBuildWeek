package database;

public enum TypeOfTransport {
	BUS, TRAM;

	@Override
	public String toString () {
		return switch (this) {
			case BUS -> "Bus";
			case TRAM -> "Tram";
		};
	}
}
