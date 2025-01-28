package main;

import dao.DAO;
import database.Maintenance;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MaintenanceDAO extends DAO<Maintenance, Long> {

	public MaintenanceDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Maintenance getById (Long id) {
		return super.getById(id, Maintenance.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, Maintenance.class);
	}

	@Override
	public List<Maintenance> findAll () {
		return super.findAll(Maintenance.class);
	}

	@Override
	public Maintenance getRandom () {
		return super.getRandom(Maintenance.class);
	}
}
