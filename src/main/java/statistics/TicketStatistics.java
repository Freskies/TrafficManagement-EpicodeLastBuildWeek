package statistics;

import java.time.LocalDate;

public class TicketStatistics {
    private LocalDate releaseDate;
    private Long count;

    public TicketStatistics(LocalDate releaseDate, Long count) {
        this.releaseDate = releaseDate;
        this.count = count;
    }

    // Getters e Setters
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TicketStatistics{" +
           "releaseDate=" + releaseDate +
           ", count=" + count +
           '}';
    }
}