package database;

import Utils.Interval;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "use_routes")
public class UseRoute {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int useRouteId;

	@ManyToOne
	@JoinColumn (name = "means_of_transport_id")
	private MeansOfTransport meansOfTransport;

	@ManyToOne
	@JoinColumn (name = "route_id")
	private Route route;

	@Column (name = "real_travel_time")
	private String realTravelTime;

	@Column (name = "date")
	private LocalDate date;

	public int getUseRouteId () {
		return this.useRouteId;
	}

	public void setUseRouteId (int useRouteId) {
		this.useRouteId = useRouteId;
	}

	public MeansOfTransport getMeansOfTransport () {
		return this.meansOfTransport;
	}

	public void setMeansOfTransport (MeansOfTransport meansOfTransport) {
		this.meansOfTransport = meansOfTransport;
	}

	public Route getRoute () {
		return this.route;
	}

	public void setRoute (Route route) {
		this.route = route;
	}

	public String getRealTravelTime () {
		return this.realTravelTime;
	}

	public void setRealTravelTime (String realTravelTime) {
		this.realTravelTime = realTravelTime;
	}

	public LocalDate getDate () {
		return this.date;
	}

	public void setDate (LocalDate date) {
		this.date = date;
	}

	public UseRoute () {

	}

	public UseRoute (MeansOfTransport meansOfTransport, Route route, String realTravelTime, LocalDate date) {
		this.setMeansOfTransport(meansOfTransport);
		this.setRoute(route);
		this.setRealTravelTime(realTravelTime);
		this.setDate(date);
	}

	public static @NotNull String averageRealTime (@NotNull List<UseRoute> routeList) {
		return Interval.secondsToTravelTime(
			routeList.stream()
				.mapToInt(route -> Interval.travelTimeToSeconds(route.getRealTravelTime()))
				.sum() / routeList.size()
		);
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof UseRoute useRoute)) return false;
		return this.getUseRouteId() == useRoute.getUseRouteId() &&
			Objects.equals(this.getMeansOfTransport(), useRoute.getMeansOfTransport()) &&
			Objects.equals(this.getRoute(), useRoute.getRoute()) &&
			Objects.equals(this.getRealTravelTime(), useRoute.getRealTravelTime()) &&
			Objects.equals(this.getDate(), useRoute.getDate());
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getUseRouteId(),
			this.getMeansOfTransport(),
			this.getRoute(),
			this.getRealTravelTime(),
			this.getDate()
		);
	}

	@Override
	public String toString () {
		return "UseRoute{" +
			"useRouteId=" + this.getUseRouteId() +
			", meansOfTransport=" + this.getMeansOfTransport() +
			", route=" + this.getRoute() +
			", realTravelTime='" + this.getRealTravelTime() + '\'' +
			", date=" + this.getDate() +
			'}';
	}
}
