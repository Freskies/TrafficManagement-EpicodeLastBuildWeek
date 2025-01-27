package main;

import dao.DAO;
import database.Ticket;
import jakarta.persistence.EntityManager;

import java.util.List;

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
}
