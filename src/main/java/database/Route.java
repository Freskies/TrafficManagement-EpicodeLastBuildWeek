package database;

import jakarta.persistence.*;
import org.postgresql.util.PGInterval;

import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "routes")
public class Route {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int routeId;

	@Column (name = "route_start")
	private String routeStart;

	@Column (name = "route_end")
	private String routeEnd;

	@Column (name = "expected_travel_time")
	private String expectedTravelTime;

	@OneToMany (mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UseRoute> useRoutes;

	public int getRouteId () {
		return this.routeId;
	}

	public void setRouteId (int routeId) {
		this.routeId = routeId;
	}

	public String getRouteStart () {
		return this.routeStart;
	}

	public void setRouteStart (String routeStart) {
		this.routeStart = routeStart;
	}

	public String getRouteEnd () {
		return this.routeEnd;
	}

	public void setRouteEnd (String routeEnd) {
		this.routeEnd = routeEnd;
	}

	public String getExpectedTravelTime () {
		return this.expectedTravelTime;
	}

	public void setExpectedTravelTime (String expectedTravelTime) {
		this.expectedTravelTime = expectedTravelTime;
	}

	public List<UseRoute> getUseRoutes () {
		return this.useRoutes;
	}

	public void setUseRoutes (List<UseRoute> useRoutes) {
		this.useRoutes = useRoutes;
	}

	public Route () {
	}

	public Route (String routeStart, String routeEnd, String expectedTravelTime) {
		this.setRouteStart(routeStart);
		this.setRouteEnd(routeEnd);
		this.setExpectedTravelTime(expectedTravelTime);
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Route route)) return false;
		return this.getRouteId() == route.getRouteId() &&
			Objects.equals(this.getRouteStart(), route.getRouteStart()) &&
			Objects.equals(this.getRouteEnd(), route.getRouteEnd()) &&
			Objects.equals(this.getExpectedTravelTime(), route.getExpectedTravelTime());
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getRouteId(),
			this.getRouteStart(),
			this.getRouteEnd(),
			this.getExpectedTravelTime()
		);
	}

	@Override
	public String toString () {
		return "Route{" +
			"routeId=" + this.getRouteId() +
			", routeStart='" + this.getRouteStart() + '\'' +
			", routeEnd='" + this.getRouteEnd() + '\'' +
			", expectedTravelTime=" + this.getExpectedTravelTime() +
			'}';
	}
}