package pt.ribas.vacation.service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pt.ribas.vacation.dto.BookVacationDTO;
import pt.ribas.vacation.dto.EmployeeDTO;
import pt.ribas.vacation.dto.RegisterEmployeeDTO;
import pt.ribas.vacation.dto.SupervisorEmployeeDTO;
import pt.ribas.vacation.entity.Employee;
import pt.ribas.vacation.entity.Vacation;
import pt.ribas.vacation.enums.Status;
import pt.ribas.vacation.repository.EmployeeRepository;
import pt.ribas.vacation.repository.VacationRepository;

@Service
@Transactional
public class VacationService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository
            .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Employee with id {0} not found", id)
            )
        );

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public void bookVacation(BookVacationDTO vacationDTO) {
        Long employeeId = vacationDTO.getEmployeeId();

        Employee employee = employeeRepository
            .findById(employeeId)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Employee with id {0} not found", employeeId)
            )
        );

        Vacation vacation = modelMapper.map(
            vacationDTO, Vacation.class
        );

        vacation.setEmployee(employee);

        vacationRepository.save(vacation);
    }

    public void registerEmployee(RegisterEmployeeDTO employeeDTO) {
        Long supervisorId = employeeDTO.getSupervisor().getId();

        Employee supervisor = employeeRepository
            .findById(supervisorId)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Employee with id {0} not found", supervisorId)
            )
        );

        if (supervisor.getSupervisor() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                MessageFormat.format("Employee with id {0} is not a supervisor", supervisorId)
            );
        }

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        employee.setSupervisor(supervisor);

        employeeRepository.save(employee);
    }

    public void alterVacationRequest(Long vacationId, Short status) {
        Vacation vacation = vacationRepository
            .findById(vacationId)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Vacation with id {0} not found", vacationId)
            )
        );

        vacation.setStatus(Status.values()[status]);
        vacation.setStatusChange(Timestamp.from(Instant.now()));

        vacationRepository.save(vacation);
    }

    public List<SupervisorEmployeeDTO> getSupervisorEmployees(Long supervisorId) {
        Employee supervisor = employeeRepository
            .findById(supervisorId)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Supervisor with id {0} not found", supervisorId)
            )
        );

        if (supervisor.getSupervisor() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                MessageFormat.format("Employee with id {0} is not a supervisor", supervisorId)
            );
        }

        List<Employee> employeesUnderSupervisor = employeeRepository.findBySupervisor(supervisor);

        return employeesUnderSupervisor.stream()
            .map(employee -> modelMapper.map(employee, SupervisorEmployeeDTO.class))
        .collect(Collectors.toList());
    }
}
