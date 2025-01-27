package main;

import dao.DAO;
import database.Card;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.NoSuchElementException;

public class CardDao extends DAO<Card, Long> {

	public CardDao (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Card getById (Long id) {
		return super.getById(id, Card.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, Card.class);
	}

	@Override
	public List<Card> findAll () {
		return super.findAll(Card.class);
	}

	public Card getLastCard (String owner) {
		this.entityManager.getTransaction().begin();
		List<Card> cards = this.entityManager.createQuery("""
			SELECT c FROM Card c
			WHERE c.ownerFullName = :owner
			ORDER BY c.releaseDate DESC
			""", Card.class
		).setParameter("owner", owner).getResultList();
		this.entityManager.getTransaction().commit();

		try {
			return cards.getFirst();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
