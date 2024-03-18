package pt.ribas.vacation.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pt.ribas.vacation.enums.Status;

@Entity
@Table(
    name = "vacations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"startDate", "endDate", "status", "employee_id"})
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private Timestamp statusChangeTime = Timestamp.from(Instant.now());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
