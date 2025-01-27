package main;

import dao.DAO;
import database.Dispenser;
import database.MeansOfTransport;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MeansOfTransportDAO extends DAO<MeansOfTransport, Long> {

	public MeansOfTransportDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public MeansOfTransport getById (Long id) {
		return super.getById(id, MeansOfTransport.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, MeansOfTransport.class);
	}

	@Override
	public List<MeansOfTransport> findAll () {
		return super.findAll(MeansOfTransport.class);
	}
}
