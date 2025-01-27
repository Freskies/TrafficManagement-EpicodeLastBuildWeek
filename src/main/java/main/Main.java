package main;

import Utils.Logger;
import dao.EntityManagerUtil;
import database.*;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	public Scanner scanner = new Scanner(System.in);

	private final CardDao cardDao;
	private final DispenserDAO dispenserDAO;
	private final MaintenanceDAO maintenanceDAO;
	private final MeansOfTransportDAO meansOfTransportDAO;
	private final RouteDAO routeDAO;
	private final SubscriptionDAO subscriptionDAO;
	private final TicketDAO ticketDAO;
	private final UseRouteDAO useRouteDAO;

	private String username;

	public String getUsername () {
		return this.username;
	}

	public void setUsername (String username) {
		if (username.isBlank())
			throw new IllegalArgumentException("Username cannot be blank");
		this.username = username;
	}

	public String scan () {
		return scanner.nextLine();
	}

	public static void main (String[] args) {
		new Main();
	}

	public Main () {
		try (EntityManager entityManager = EntityManagerUtil.getEntityManager()) {
			this.cardDao = new CardDao(entityManager);
			this.dispenserDAO = new DispenserDAO(entityManager);
			this.maintenanceDAO = new MaintenanceDAO(entityManager);
			this.meansOfTransportDAO = new MeansOfTransportDAO(entityManager);
			this.routeDAO = new RouteDAO(entityManager);
			this.subscriptionDAO = new SubscriptionDAO(entityManager);
			this.ticketDAO = new TicketDAO(entityManager);
			this.useRouteDAO = new UseRouteDAO(entityManager);

			//Logger logger = new Logger();
			//logger.log(this.dispenserDAO.findAll());

			this.mainMenu();
		} finally {
			EntityManagerUtil.closeEntityManagerFactory();
		}
	}

	// MAIN MENU

	public void mainMenu () {
		System.out.print("""
			1. log in as user [USER MENU]
			2. log in as admin [ADMIN MENU]
			0. exit
			->\s""");

		switch (this.scan()) {
			case "1" -> {
				this.selectUser();
				this.userMenu();
			}
			case "2" -> {
				this.selectUser();
				this.adminMenu();
			}
			case "0" -> System.exit(0);
			default -> {
				System.out.println("Invalid input");
				this.mainMenu();
			}
		}
	}

	// USER MENU

	public void selectUser () {
		System.out.print("Insert your name: ");
		try {
			this.setUsername(this.scan());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			this.selectUser();
		}
	}

	public void userMenu () {
		System.out.print("""
			1. dispencer
			2. use means of transport
			0. back
			->\s""");

		switch (this.scan()) {
			case "1" -> this.selectDispencer();
			case "2" -> this.transport();
			case "0" -> this.mainMenu();
			default -> {
				System.out.println("invalid input");
				this.userMenu();
			}
		}
	}

	// DISPENCER

	public void selectDispencer () {
		AtomicInteger index = new AtomicInteger();
		List<Dispenser> dispenserList = this.dispenserDAO.findAllActive();

		System.out.println("Select a dispenser: ");
		dispenserList.forEach(dispenser ->
			System.out.println(index.incrementAndGet() + ". " + dispenser.getLocation())
		);
		System.out.print("""
			0. back
			->\s""");

		String input = this.scan();
		if (input.equals("0")) this.userMenu();

		try {
			int choice = Integer.parseInt(input);
			Dispenser dispenser = dispenserList.get(choice - 1);
			this.dispencerMenu(dispenser);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid input");
			this.selectDispencer();
		}
	}

	public void dispencerMenu (@NotNull Dispenser dispenser) {
		System.out.printf("""
			DISPENCER %s
			1. buy ticket
			2. compile card
			3. check subscription
			0. back
			->\s""", dispenser.getLocation());

		switch (this.scan()) {
			case "1" -> this.buyTicket(dispenser);
			case "2" -> this.compileCard(dispenser);
			case "3" -> this.checkSubscription();
			case "0" -> this.userMenu();
			default -> {
				System.out.println("Invalid input");
				this.dispencerMenu(dispenser);
			}
		}
	}

	public void buyTicket (Dispenser dispenser) {
		Ticket ticket = new Ticket(LocalDate.now(), dispenser);
		this.ticketDAO.save(ticket);
		System.out.printf("Ticket purchased successfully: %s\n", ticket.getTicketId());
		this.dispencerMenu(dispenser);
	}

	public void compileCard (Dispenser dispenser) {
		Card lastCard = this.cardDao.getLastCard(this.getUsername());
		if (lastCard == null) {
			Card card = new Card(this.getUsername(), LocalDate.now(), dispenser);
			this.cardDao.save(card);
			System.out.printf("Card compiled successfully: %s\n", card.getCardId());
		}
		else if (lastCard.isActive())
			System.out.println("Card is already active! Is active from " + lastCard.getReleaseDate());
		else {
			Card card = new Card(this.getUsername(), LocalDate.now(), dispenser);
			this.cardDao.save(card);
			System.out.println("Card renewed successfully!");
		}
		this.dispencerMenu(dispenser);
	}

	public void checkSubscription () {

	}


	// TRANSPORT

	public void transport () {
		System.out.println("""
			CIOLA""");


	}

	// ADMIN MENU

	public void adminMenu () {

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
	2. use means of transport
	2.x> select means of transport
	2.x.> check subscription
	2.x.> insert ticked id
	2.x.> ciola

	DISPENCER MENU
	1. buy ticket
	1.> ticket_id
	2. compile card | renew card | null
	3 (card exist) (no subscription). buy subscription
	3.1 weekly / monthly

	ADMIN MENU
	1.
 */