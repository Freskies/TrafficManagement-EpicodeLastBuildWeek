package main;

import dao.DAO;
import database.Subscription;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SubscriptionDAO extends DAO<Subscription, Long> {

	public SubscriptionDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Subscription getById (Long id) {
		return super.getById(id, Subscription.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, Subscription.class);
	}

	@Override
	public List<Subscription> findAll () {
		return super.findAll(Subscription.class);
	}
}
