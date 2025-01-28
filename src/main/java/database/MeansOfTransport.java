package database;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "means_of_transport")
public class MeansOfTransport {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int meansOfTransportId;

	@Enumerated (EnumType.STRING)
	@Column (name = "type_of_transport")
	private TypeOfTransport typeOfTransport;

	@Column (name = "model")
	private String model;

	@Column (name = "seats")
	private int seats;

	@OneToMany (mappedBy = "meansOfTransport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Maintenance> maintenances;

	@OneToMany (mappedBy = "meansOfTransport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UseRoute> useRoutes;

	public int getMeansOfTransportId () {
		return this.meansOfTransportId;
	}

	public void setMeansOfTransportId (int meansOfTransportId) {
		this.meansOfTransportId = meansOfTransportId;
	}

	public TypeOfTransport getTypeOfTransport () {
		return this.typeOfTransport;
	}

	public void setTypeOfTransport (TypeOfTransport typeOfTransport) {
		this.typeOfTransport = typeOfTransport;
	}

	public String getModel () {
		return this.model;
	}

	public void setModel (String model) {
		this.model = model;
	}

	public int getSeats () {
		return this.seats;
	}

	public void setSeats (int seats) {
		this.seats = seats;
	}

	public List<Maintenance> getMaintenances () {
		return this.maintenances;
	}

	public void setMaintenances (List<Maintenance> maintenances) {
		this.maintenances = maintenances;
	}

	public List<UseRoute> getUseRoutes () {
		return this.useRoutes;
	}

	public void setUseRoutes (List<UseRoute> useRoutes) {
		this.useRoutes = useRoutes;
	}

	public MeansOfTransport () {
	}

	public MeansOfTransport (TypeOfTransport typeOfTransport, String model, int seats) {
		this.setTypeOfTransport(typeOfTransport);
		this.setModel(model);
		this.setSeats(seats);
	}

	@Override
	public boolean equals (Object o) {
		if (!(o instanceof MeansOfTransport that)) return false;
		return this.getMeansOfTransportId() == that.getMeansOfTransportId() &&
			this.getSeats() == that.getSeats() &&
			this.getTypeOfTransport() == that.getTypeOfTransport() &&
			Objects.equals(this.getModel(), that.getModel());
	}

	@Override
	public int hashCode () {
		return Objects.hash(
			this.getMeansOfTransportId(),
			this.getTypeOfTransport(),
			this.getModel(),
			this.getSeats()
		);
	}

	@Override
	public String toString () {
		return "MeansOfTransport{" +
			"meansOfTransportId=" + this.getMeansOfTransportId() +
			", typeOfTransport=" + this.getTypeOfTransport() +
			", model='" + this.getModel() + '\'' +
			", seats=" + this.getSeats() +
			'}';
	}
}