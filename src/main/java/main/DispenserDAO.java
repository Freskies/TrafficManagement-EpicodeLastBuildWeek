package main;

import dao.DAO;
import database.Dispenser;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DispenserDAO extends DAO<Dispenser, Long> {

	public DispenserDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Dispenser getById (Long id) {
		return super.getById(id, Dispenser.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, Dispenser.class);
	}

	@Override
	public List<Dispenser> findAll () {
		return super.findAll(Dispenser.class);
	}

	@Override
	public Dispenser getRandom () {
		return super.getRandom(Dispenser.class);
	}

	public List<Dispenser> findAllActive () {
		return this.findAll().stream().filter(Dispenser::isActive).toList();
	}
}
