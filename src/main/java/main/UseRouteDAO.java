package main;

import dao.DAO;
import database.UseRoute;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UseRouteDAO extends DAO<UseRoute, Long> {

	public UseRouteDAO (EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public UseRoute getById (Long id) {
		return super.getById(id, UseRoute.class);
	}

	@Override
	public void deleteById (Long id) {
		super.deleteById(id, UseRoute.class);
	}

	@Override
	public List<UseRoute> findAll () {
		return super.findAll(UseRoute.class);
	}

	@Override
	public UseRoute getRandom () {
		return super.getRandom(UseRoute.class);
	}
}
