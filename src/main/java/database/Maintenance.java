package database;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "maintenances")
public class Maintenance {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int maintenanceId;

	@Column (name = "start_date")
	private LocalDate startDate;

	@Column (name = "end_date")
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn (name = "means_of_transport_id")
	private MeansOfTransport meansOfTransport;

	public int getMaintenanceId () {
		return this.maintenanceId;
	}

	public void setMaintenanceId (int maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	public LocalDate getStartDate () {
		return this.startDate;
	}

	public void setStartDate (LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate () {
		return this.endDate;
	}

	public void setEndDate (LocalDate endDate) {
		this.endDate = endDate;
	}

	public MeansOfTransport getMeansOfTransport () {
		return this.meansOfTransport;
	}

	public void setMeansOfTransport (MeansOfTransport meansOfTransport) {
		this.meansOfTransport = meansOfTransport;
	}

	public Maintenance () {

	}

	public Maintenance (LocalDate startDate, LocalDate endDate, MeansOfTransport meansOfTransport) {
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setMeansOfTransport(meansOfTransport);
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Maintenance that)) return false;
		return this.getMaintenanceId() == that.getMaintenanceId() &&
			Objects.equals(this.getStartDate(), that.getStartDate()) &&
			Objects.equals(this.getEndDate(), that.getEndDate()) &&
			Objects.equals(this.getMeansOfTransport(), that.getMeansOfTransport());
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getMaintenanceId(),
			this.getStartDate(),
			this.getEndDate(),
			this.getMeansOfTransport()
		);
	}

	@Override
	public String toString () {
		return "Maintenance{" +
			"maintenanceId=" + this.getMaintenanceId() +
			", startDate=" + this.getStartDate() +
			", endDate=" + this.getEndDate() +
			", meansOfTransport=" + this.getMeansOfTransport() +
			'}';
	}
}
