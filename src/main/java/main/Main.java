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




/*
	MAIN MENU
	1. log in as user [USER MENU]
	2. log in as admin [ADMIN MENU]
	0. exit

	USER MENU
	1. dispencer [DISPENCER MENU]
	1.x> select dispencer

	DISPENCER MENU
	1. buy ticket
	1.> ticket_id
	2. compile card | renew card | null
	3 (card exist) (no subscription). buy subscription
	3.1 weekly / monthly

	ADMIN MENU
 */