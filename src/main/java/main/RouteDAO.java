package main;

import dao.DAO;
import database.Route;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RouteDAO extends DAO<Route, Long> {

	public RouteDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Route getById (Long id) {
		return super.getById(id, Route.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, Route.class);
	}

	@Override
	public List<Route> findAll () {
		return super.findAll(Route.class);
	}

	@Override
	public Route getRandom () {
		return super.getRandom(Route.class);
	}
}
