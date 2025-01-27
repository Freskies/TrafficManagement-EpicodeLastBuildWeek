package dao;

import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class DAO<T, IdType> {
	protected EntityManager entityManager;

	public DAO (EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void save (T o) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(o);
		this.entityManager.getTransaction().commit();
	}

	public abstract T getById (IdType id);

	protected T getById (IdType id, Class<T> tClass) {
		this.entityManager.getTransaction().begin();
		T o = this.entityManager.find(tClass, id);
		this.entityManager.getTransaction().commit();
		return o;
	}

	public abstract void deleteById (IdType id);

	protected void deleteById (IdType id, Class<T> tClass) {
		this.entityManager.getTransaction().begin();
		T o = this.entityManager.find(tClass, id);
		this.entityManager.remove(o);
		this.entityManager.getTransaction().commit();
	}

	public void delete (T o) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(o);
		this.entityManager.getTransaction().commit();
	}

	public abstract List<T> findAll ();

	protected List<T> findAll (Class<T> tClass) {
		try {
			this.entityManager.getTransaction().begin();
			List<T> o = this.entityManager.createQuery(
				"SELECT foo FROM %s foo".formatted(tClass.getSimpleName()), tClass
			).getResultList();
			this.entityManager.getTransaction().commit();
			return o;
		} catch (Exception e) {
			System.err.println("An error occurred: " + e.getMessage());
			return null;
		}
	}
}
