package pt.ribas.vacation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ribas.vacation.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
