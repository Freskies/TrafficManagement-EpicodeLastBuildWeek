package main;

import dao.DAO;
import database.Route;
import database.UseRoute;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

	public HashMap<Route, List<UseRoute>> getUsedRoutesByRoute () {
		List<UseRoute> useRouteList = this.findAll();
		return useRouteList.stream().collect(Collectors.toMap(
			UseRoute::getRoute,
			List::of,
			(u1, u2) -> {
				List<UseRoute> u = new ArrayList<>(u1);
				u.addAll(u2);
				return u;
			},
			HashMap::new
		));
	}

}
