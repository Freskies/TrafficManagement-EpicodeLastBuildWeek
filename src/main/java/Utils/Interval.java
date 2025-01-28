package Utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Interval {
	public static int travelTimeToSeconds (String travelTime) {
		if (travelTime == null)
			throw new IllegalArgumentException("Travel time cannot be null");
		if (!travelTime.matches("\\d{2}:\\d{2}:\\d{2}"))
			throw new IllegalArgumentException("Invalid travel time format");

		String[] time = travelTime.split(":");
		int hours = Integer.parseInt(time[0]);
		int minutes = Integer.parseInt(time[1]);
		int seconds = Integer.parseInt(time[2]);
		return hours * 3600 + minutes * 60 + seconds;
	}

	@Contract (pure = true)
	public static @NotNull String secondsToTravelTime (int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		int sec = seconds % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, sec);
	}
}
