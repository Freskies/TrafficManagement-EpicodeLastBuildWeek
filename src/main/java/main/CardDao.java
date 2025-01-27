package main;

import dao.DAO;
import database.Card;
import jakarta.persistence.EntityManager;

import java.util.List;

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
}
