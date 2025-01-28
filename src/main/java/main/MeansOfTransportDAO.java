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

	@Override
	public MeansOfTransport getRandom () {
		return super.getRandom(MeansOfTransport.class);
	}

	public List<MeansOfTransport> findAllActive () {
		super.entityManager.getTransaction().begin();
		List<MeansOfTransport> meansOfTransports = super.entityManager.createQuery("""
			SELECT mof
			FROM MeansOfTransport mof, Maintenance m
			WHERE mof.meansOfTransportId = m.meansOfTransport.meansOfTransportId
			AND m.endDate < CURRENT_DATE
			""", MeansOfTransport.class).getResultList();
		super.entityManager.getTransaction().commit();
		return meansOfTransports;
	}
}
