package main;

import dao.EntityManagerUtil;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class Main {
	public static Scanner scanner = new Scanner(System.in);

	public static String scan () {
		return scanner.nextLine();
	}

	public static void main (String[] args) {
		try (EntityManager entityManager = EntityManagerUtil.getEntityManager()) {
			CardDao cardDao = new CardDao(entityManager);
		} finally {
			EntityManagerUtil.closeEntityManagerFactory();
		}
	}

	public static void mainMenu () {

	}
}
