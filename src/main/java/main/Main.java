package main;

import com.github.javafaker.Faker;
import dao.EntityManagerUtil;
import database.*;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
		if (username == null || username.isBlank())
			throw new IllegalArgumentException("Username cannot be blank");
		if (username.length() > 50)
			throw new IllegalArgumentException("Username is too long");
		if (username.length() < 3)
			throw new IllegalArgumentException("Username is too short");
		if (!username.matches("^[a-zA-Z]*$"))
			throw new IllegalArgumentException("Username can only contain letters");
		this.username = username.trim();
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

			this.mainMenu();
		} finally {
			EntityManagerUtil.closeEntityManagerFactory();
		}
	}

	// MAIN MENU

	public void mainMenu () {
		System.out.print("""
			1. log in as user
			2. log in as admin
			0. exit
			->\s""");

		switch (this.scan()) {
			case "1" -> {
				this.selectUser();
				this.userMenu();
			}
			case "2" -> this.adminMenu();
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
			case "1" -> this.selectDispenser();
			case "2" -> this.selectMeansOfTransport();
			case "0" -> this.mainMenu();
			default -> {
				System.out.println("invalid input");
				this.userMenu();
			}
		}
	}

	// DISPENSER

	public void selectDispenser () {
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
			this.dispenserMenu(dispenser);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			System.out.println("Invalid input");
			this.selectDispenser();
		}
	}

	public void dispenserMenu (@NotNull Dispenser dispenser) {
		System.out.printf("""
			DISPENSER %s
			1. buy ticket
			2. compile card
			3. buy subscription
			0. back
			->\s""", dispenser.getLocation());

		switch (this.scan()) {
			case "1" -> this.buyTicket(dispenser);
			case "2" -> this.compileCard(dispenser);
			case "3" -> this.buySubscription(dispenser);
			case "0" -> this.userMenu();
			default -> {
				System.out.println("Invalid input");
				this.dispenserMenu(dispenser);
			}
		}
	}

	public void buyTicket (Dispenser dispenser) {
		Ticket ticket = new Ticket(LocalDate.now(), dispenser);
		this.ticketDAO.save(ticket);
		System.out.printf("Ticket purchased successfully: %s\n", ticket.getTicketId());
		this.dispenserMenu(dispenser);
	}

	public void compileCard (Dispenser dispenser) {
		Card lastCard = this.cardDao.getLastCard(this.getUsername());

		if (lastCard == null) {
			Card card = new Card(this.getUsername(), LocalDate.now(), dispenser);
			this.cardDao.save(card);
			System.out.printf("Card compiled successfully: %s\n", card.getCardId());
		} else if (lastCard.isActive())
			System.out.println("Card is already active! Is active from " + lastCard.getReleaseDate());
		else {
			Card card = new Card(this.getUsername(), LocalDate.now(), dispenser);
			this.cardDao.save(card);
			System.out.println("Card renewed successfully!");
		}
		this.dispenserMenu(dispenser);
	}

	public void buySubscription (Dispenser dispenser) {
		Card card = this.cardDao.getLastCard(this.getUsername());
		if (card == null) {
			System.out.println("You need a card to buy a subscription");
			this.dispenserMenu(dispenser);
		}
		if (this.subscriptionDAO.hasSubscription(card)) {
			System.out.println("You already have a subscription!");
			this.dispenserMenu(dispenser);
		} else {
			System.out.print("""
				1. weekly
				2. monthly
				0. cancel
				->\s""");

			switch (this.scan()) {
				case "1" -> {
					Subscription subscription = new Subscription(
						card,
						dispenser,
						LocalDate.now(),
						SubscriptionDuration.WEEKLY
					);
					this.subscriptionDAO.save(subscription);
					System.out.println("Weekly subscription purchased successfully!");
				}
				case "2" -> {
					Subscription subscription = new Subscription(
						card,
						dispenser,
						LocalDate.now(),
						SubscriptionDuration.MONTHLY
					);
					this.subscriptionDAO.save(subscription);
					System.out.println("Monthly subscription purchased successfully!");
				}
				case "0" -> this.dispenserMenu(dispenser);
				default -> {
					System.out.println("Invalid input");
					this.buySubscription(dispenser);
				}
			}
		}

		this.dispenserMenu(dispenser);
	}

	// TRANSPORT

	public void selectMeansOfTransport () {
		AtomicInteger index = new AtomicInteger();
		List<MeansOfTransport> meansOfTransportList = this.meansOfTransportDAO.findAllActive();

		System.out.println("Select a means of transport: ");
		meansOfTransportList.forEach(meansOfTransport ->
			System.out.printf("%d. %s %s\n",
				index.incrementAndGet(),
				meansOfTransport.getTypeOfTransport(),
				meansOfTransport.getModel()
			)
		);
		System.out.print("""
			0. back
			->\s""");

		String input = this.scan();
		if (input.equals("0")) this.userMenu();

		try {
			int choice = Integer.parseInt(input);
			MeansOfTransport meansOfTransport = meansOfTransportList.get(choice - 1);

			this.validateTicket(meansOfTransport);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid input");
			this.selectMeansOfTransport();
		}
	}

	public void validateTicket (MeansOfTransport meansOfTransport) {
		Card card = this.cardDao.getLastCard(this.getUsername());
		if (card != null && this.subscriptionDAO.hasSubscription(card))
			this.transportMenu(meansOfTransport, null);
		else
			this.useTicket(meansOfTransport);
	}

	public void useTicket (MeansOfTransport meansOfTransport) {
		try {
			System.out.print("Insert ticket id (type back to go back): ");
			String ticketId = this.scan();

			if (ticketId.trim().equalsIgnoreCase("back"))
				this.selectMeansOfTransport();

			Ticket ticket = this.ticketDAO.getById(Long.parseLong(ticketId));

			if (ticket == null) {
				System.out.println("Invalid ticket id");
				this.useTicket(meansOfTransport);
			} else if (ticket.getUsedRoute() != null) {
				System.out.println("Ticket already used");
				this.useTicket(meansOfTransport);
			} else {
				System.out.println("Valid ticket!");
				this.transportMenu(meansOfTransport, ticket);
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid ticket id");
			this.useTicket(meansOfTransport);
		}

	}

	public void transportMenu (@NotNull MeansOfTransport meansOfTransport, Ticket ticket) {
		Route random_route = this.routeDAO.getRandom();
		if (random_route == null) random_route = this.createRandomRoute();
		UseRoute useRoute = new UseRoute(
			meansOfTransport,
			random_route,
			this.createRandomInterval(),
			LocalDate.now()
		);
		this.useRouteDAO.save(useRoute);

		if (ticket != null) {
			ticket.setUsedRoute(useRoute);
			this.ticketDAO.save(ticket);
		}

		this.printUsedRoute(useRoute);
		this.userMenu();
	}

	public void printUsedRoute (@NotNull UseRoute usedRoute) {
		System.out.printf("""
				You traveled from %s to %s in %s!
				""",
			usedRoute.getRoute().getRouteStart(),
			usedRoute.getRoute().getRouteEnd(),
			usedRoute.getRealTravelTime()
		);
	}

	public Route createRandomRoute () {
		Faker faker = new Faker(Locale.ENGLISH);
		Route random_route = new Route(
			faker.address().city(),
			faker.address().city(),
			this.createRandomInterval()
		);
		this.routeDAO.save(random_route);
		return random_route;
	}

	public String createRandomInterval () {
		return "%02d:%02d:%02d".formatted(
			(int) (Math.random() * 3),
			(int) (Math.random() * 60),
			(int) (Math.random() * 60)
		);
	}

	// ADMIN MENU

	@SuppressWarnings ("InfiniteRecursion")
	public void adminMenu () {
		System.out.print("""
			ADMIN CONSOLE:
			1. Get tickets obliterated grouped by date
			2. Get tickets by dispenser
			3. Get tickets by means of transport
			4. Average real travel time for each route
			0. Back
			->\s""");

		switch (this.scan()) {
			case "1" -> this.getTicketsObliteratedGroupedByDate();
			case "2" -> this.getTicketsByDispenser();
			case "3" -> this.getTicketsByMeansOfTransport();
			case "4" -> this.averageTravelTimePerRoute();
			case "0" -> this.mainMenu();
			default -> {
				System.out.println("Invalid input");
				this.adminMenu();
			}
		}

		this.adminMenu();
	}

	public void getTicketsObliteratedGroupedByDate () {
		HashMap<LocalDate, List<Ticket>> res = this.ticketDAO.getTicketsByDays(
			this.scanDate("Insert start date"),
			this.scanDate("Insert end date")
		);
		res.forEach(
			(date, tickets) ->
				System.out.println(date + ": " + tickets.size())
		);
	}

	public void getTicketsByDispenser () {
		List<Dispenser> dispenserList = this.dispenserDAO.findAll();
		for (Dispenser dispenser : dispenserList)
			if (!dispenser.getTickets().isEmpty())
				System.out.println(dispenser.getLocation() + ": " + dispenser.getTickets().size());
	}

	public void getTicketsByMeansOfTransport () {
		HashMap<MeansOfTransport, List<Ticket>> res = this.ticketDAO.getTicketsByMeansOfTransport();
		res.forEach(
			(meansOfTransport, tickets) ->
				System.out.println(meansOfTransport.getModel() + ": " + tickets.size())
		);
	}

	public void averageTravelTimePerRoute () {
		HashMap<Route, List<UseRoute>> res = this.useRouteDAO.getUsedRoutesByRoute();

		res.forEach(
			(route, useRoutes) ->
				System.out.printf("""
						%s to %s: %s expected but %s on average
						""",
					route.getRouteStart(),
					route.getRouteEnd(),
					route.getExpectedTravelTime(),
					UseRoute.averageRealTime(useRoutes)
				)
		);
	}

	// ASK DATE

	public LocalDate scanDate (String ask) {
		System.out.print(ask + " (yyyy-mm-dd): ");
		String date = this.scan();
		try {
			return LocalDate.parse(date);
		} catch (Exception e) {
			System.out.println("Invalid date format");
			return this.scanDate(ask);
		}
	}
}