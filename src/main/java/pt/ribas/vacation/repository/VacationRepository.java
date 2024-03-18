package pt.ribas.vacation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ribas.vacation.entity.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {}
