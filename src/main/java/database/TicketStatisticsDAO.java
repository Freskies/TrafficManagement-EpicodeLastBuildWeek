package database;

import Utils.Logger;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import dao.EntityManagerUtil;

import database.TicketStatistics;

public class TicketStatisticsDAO {

    private final EntityManager entityManager;

    // Costruttore per accettare un EntityManager
    public TicketStatisticsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<TicketStatistics> getTicketStatistics(LocalDate startDate, LocalDate endDate) {
        String hql = """
                    SELECT new database.TicketStatistics(
                        t.releaseDate,
                        COUNT(t.releaseDate)
                    )
                    FROM
                        Ticket t
                    INNER JOIN
                        t.dispenser d
                    WHERE
                        t.releaseDate BETWEEN :startDate AND :endDate
                    GROUP BY
                        t.releaseDate
                    ORDER BY
                        t.releaseDate
                """;

        return entityManager.createQuery(hql, TicketStatistics.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public static void main(String[] args) {
        // Creazione di EntityManager e gestione risorse
        try (EntityManager entityManager = EntityManagerUtil.getEntityManager()) {
            // Creazione del DAO
            TicketStatisticsDAO ticketStatisticsDAO = new TicketStatisticsDAO(entityManager);

            // Date di esempio
            LocalDate startDate = LocalDate.of(2023, 5, 1);
            LocalDate endDate = LocalDate.of(2024, 1, 1);

            // Esecuzione della query e log dei risultati
            List<TicketStatistics> statistics = ticketStatisticsDAO.getTicketStatistics(startDate, endDate);

            Logger stats = new Logger();
            stats.log(statistics);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Chiusura del factory
            EntityManagerUtil.closeEntityManagerFactory();
        }
    }
}
