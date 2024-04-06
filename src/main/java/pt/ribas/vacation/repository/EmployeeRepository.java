package pt.ribas.vacation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ribas.vacation.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findBySupervisor(Employee supervisor);
}
