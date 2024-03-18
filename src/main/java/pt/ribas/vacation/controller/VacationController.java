package pt.ribas.vacation.controller;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import pt.ribas.vacation.dto.BookVacationDTO;
import pt.ribas.vacation.dto.EmployeeDTO;
import pt.ribas.vacation.service.VacationService;

@RestController
@CrossOrigin
public class VacationController {
    @Autowired
    private VacationService service;

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return service.getEmployee(id);
    }

    @PostMapping("/book-vacation")
    public void bookVacation(
        @Valid @RequestBody BookVacationDTO vacationDTO
    ) {
        try {
            service.bookVacation(vacationDTO);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                MessageFormat.format(
                    "Employee with id {0} has already requested" + 
                    " a vacation in the timespan {1} - {2}",
                    vacationDTO.getEmployeeId(),
                    vacationDTO.getStartDate(),
                    vacationDTO.getEndDate()
                )
            );
        }
    }

    @GetMapping("/register-employee")
    public void registerEmployee() {

    }

    @GetMapping("/alter-vacation-request")
    public void alterVacationRequest() {

    }
}
