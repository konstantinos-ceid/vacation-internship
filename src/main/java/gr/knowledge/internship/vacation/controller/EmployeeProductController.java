package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.EmployeeProductService;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.utility.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeProductController {
    private final EmployeeProductService employeeProductService;

    public EmployeeProductController(EmployeeProductService employeeProductService) {
        this.employeeProductService = employeeProductService;
    }

    @PostMapping("/employeeProduct")
    public ResponseEntity<EmployeeProductDTO> save(@RequestBody EmployeeProductDTO employeeProductDTO) {
        log.debug("Rest request to save EmployeeProduct : {}", employeeProductDTO);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/employeeProduct/{id}")
    public ResponseEntity<EmployeeProductDTO> update(@PathVariable("id") Long id, @RequestBody EmployeeProductDTO employeeProductDTO) {
        log.debug("Rest request to save EmployeeProduct : {}", employeeProductDTO);
        Boolean isExistingEmployeeProduct = employeeProductService.isExistingEmployeeProduct(id);
        Util.validateUpdateInput(employeeProductDTO.getId(), id, isExistingEmployeeProduct);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/employeeProduct/{id}")
    public ResponseEntity<EmployeeProductDTO> getById(@PathVariable("id") Long id) {
        log.debug("Rest request to get EmployeeProduct by id : {}", id);
        EmployeeProductDTO employeeProduct = employeeProductService.getById(id);
        return new ResponseEntity<>(employeeProduct, HttpStatus.OK);
    }

    @GetMapping("/employeeProduct")
    public ResponseEntity<List<EmployeeProductDTO>> getAll() {
        log.debug("Rest request to get all EmployeeProduct ");
        List<EmployeeProductDTO> employeeProductDTOList = employeeProductService.getAll();
        return new ResponseEntity<>(employeeProductDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/employeeProduct/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("Rest Request to delete EmployeeProduct by id : {}", id);
        Boolean isExistingEmployeeProduct = employeeProductService.isExistingEmployeeProduct(id);
        Util.validateDeleteInput(isExistingEmployeeProduct);
        employeeProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

}