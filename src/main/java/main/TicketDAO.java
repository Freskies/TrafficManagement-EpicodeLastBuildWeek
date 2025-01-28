package main;

import dao.DAO;
import database.MeansOfTransport;
import database.Ticket;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDAO extends DAO<Ticket, Long> {

	public TicketDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Ticket getById (Long id) {
		return super.getById(id, Ticket.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, Ticket.class);
	}

	@Override
	public List<Ticket> findAll () {
		return super.findAll(Ticket.class);
	}

	@Override
	public Ticket getRandom () {
		return super.getRandom(Ticket.class);
	}

	public HashMap<LocalDate, List<Ticket>> getTicketsByDays (LocalDate startDate, LocalDate endDate) {
		List<Ticket> tickets = this.findAll().stream().filter(Ticket::isObliterated).toList();
		return tickets.stream().collect(Collectors.toMap(
			ticket -> ticket.getUsedRoute().getDate(),
			ticket -> List.of(new Ticket[] {ticket}),
			(t1, t2) -> {
				List<Ticket> t = new ArrayList<>(List.copyOf(t1));
				t.addAll(t2);
				return t;
			},
			HashMap::new
		));
	}

	public HashMap<MeansOfTransport, List<Ticket>> getTicketsByMeansOfTransport () {
		List<Ticket> tickets = this.findAll().stream().filter(Ticket::isObliterated).toList();
		return tickets.stream().collect(Collectors.toMap(
			ticket -> ticket.getUsedRoute().getMeansOfTransport(),
			List::of,
			(t1, t2) -> {
				List<Ticket> t = new ArrayList<>(List.copyOf(t1));
				t.addAll(t2);
				return t;
			},
			HashMap::new
		));
	}
}
