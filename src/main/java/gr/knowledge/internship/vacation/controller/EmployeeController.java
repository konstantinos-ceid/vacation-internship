package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.EmployeeService;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.utility.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> save(@RequestBody EmployeeDTO employeeDTO) {
        log.debug("Rest request to save Employee : {}", employeeDTO);
        EmployeeDTO result = employeeService.save(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        log.debug("Rest request to save Employee : {}", employeeDTO);
        Boolean isExistingEmployee = employeeService.isExistingEmployee(id);
        Util.validateUpdateInput(employeeDTO.getId(), id, isExistingEmployee);
        EmployeeDTO result = employeeService.save(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable("id") Long id) {
        log.debug("Rest request to get Employee by id : {}", id);
        EmployeeDTO employee = employeeService.getById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        log.debug("Rest request to get all Employee ");
        List<EmployeeDTO> employeeDTOList = employeeService.getAll();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("Rest Request to delete Employee by id : {}", id);
        Boolean isExistingEmployee = employeeService.isExistingEmployee(id);
        Util.validateDeleteInput(isExistingEmployee);
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}