package pt.ribas.vacation.service;

import java.text.MessageFormat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pt.ribas.vacation.dto.BookVacationDTO;
import pt.ribas.vacation.dto.EmployeeDTO;
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
}
