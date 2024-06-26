package pt.ribas.vacation.service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pt.ribas.vacation.dto.AlterVacationDTO;
import pt.ribas.vacation.dto.BookVacationDTO;
import pt.ribas.vacation.dto.EmployeeDTO;
import pt.ribas.vacation.dto.RegisterEmployeeDTO;
import pt.ribas.vacation.dto.SupervisorEmployeeDTO;
import pt.ribas.vacation.entity.Employee;
import pt.ribas.vacation.entity.Vacation;
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

    @Autowired
    private BCryptPasswordEncoder encoder;

    public EmployeeDTO getEmployee(String email) {
        Employee employee = employeeRepository
            .findByEmail(email)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Employee with email {0} not found", email)
            )
        );

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public void bookVacation(BookVacationDTO vacationDTO, String email) {
        Employee employee = employeeRepository
            .findByEmail(email)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Employee with email {0} not found", email)
            )
        );

        Vacation vacation = modelMapper.map(
            vacationDTO, Vacation.class
        );

        vacation.setEmployee(employee);

        vacationRepository.save(vacation);
    }

    public void registerEmployee(RegisterEmployeeDTO employeeDTO) {
        String supervisorEmail = employeeDTO.getSupervisor().getEmail();

        Employee supervisor = employeeRepository
            .findByEmail(supervisorEmail)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Employee with email {0} not found", supervisorEmail)
            )
        );

        if (supervisor.getSupervisor() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                MessageFormat.format("Employee with email {0} is not a supervisor", supervisorEmail)
            );
        }

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        employee.setSupervisor(supervisor);

        employee.setPassword(encoder.encode(employee.getPassword()));

        employeeRepository.save(employee);
    }

    public void alterVacationRequest(AlterVacationDTO alterVacationDTO) {
        Long vacationId = alterVacationDTO.getVacationId();

        Vacation vacation = vacationRepository
            .findById(vacationId)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Vacation with id {0} not found", vacationId)
            )
        );

        vacation.setStatus(alterVacationDTO.getStatus());
        vacation.setStatusChange(Timestamp.from(Instant.now()));

        vacationRepository.save(vacation);
    }

    public List<SupervisorEmployeeDTO> getSupervisorEmployees(String supervisorEmail) {
        Employee supervisor = employeeRepository
            .findByEmail(supervisorEmail)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format(
                    "Supervisor with email {0} not found",
                    supervisorEmail
                )
            )
        );

        if (supervisor.getSupervisor() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                MessageFormat.format(
                    "Employee with email {0} is not a supervisor",
                    supervisorEmail
                )
            );
        }

        List<Employee> employeesUnderSupervisor = employeeRepository.findBySupervisor(supervisor);

        return employeesUnderSupervisor.stream()
            .map(employee -> modelMapper.map(employee, SupervisorEmployeeDTO.class))
        .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
            .stream()
            .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
        .collect(Collectors.toList());
    }
}
