package database;

import Utils.Logger;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

public class TicketStatisticsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<TicketStatistics> getTicketStatistics(LocalDate startDate, LocalDate endDate) {
        String hql = """
                    SELECT new com.example.statistics.TicketStatistics(
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
        TicketStatisticsDAO dao = new TicketStatisticsDAO();

        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        Logger stats = new Logger();
        List<TicketStatistics> statistics = dao.getTicketStatistics(startDate, endDate);
        stats.log(statistics);
    }
}
