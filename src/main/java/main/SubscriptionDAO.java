package main;

import dao.DAO;
import database.Card;
import database.Subscription;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.NoSuchElementException;

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

	@Override
	public Subscription getRandom () {
		return super.getRandom(Subscription.class);
	}

	public boolean hasSubscription (Card card) {
		super.entityManager.getTransaction().begin();
		List<Subscription> subscriptions = super.entityManager.createQuery("""
				SELECT s FROM Subscription s
				WHERE s.card = :card
				ORDER BY s.releaseDate DESC""",
			Subscription.class
		).setParameter("card", card).getResultList();
		super.entityManager.getTransaction().commit();
		try {
			Subscription lastSubscription = subscriptions.getFirst();
			return lastSubscription.isActive();
		} catch (NoSuchElementException _) {
			return false;
		}
	}
}
