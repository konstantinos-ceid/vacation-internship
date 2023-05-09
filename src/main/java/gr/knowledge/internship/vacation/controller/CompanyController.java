package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.CompanyService;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.utility.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Log4j2
@CrossOrigin
@RequestMapping("/api")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company")
    public ResponseEntity<CompanyDTO> save(@RequestBody CompanyDTO companyDTO) {
        log.debug("Rest request to save Company : {}", companyDTO);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable("id") Long id, @RequestBody CompanyDTO companyDTO) {
        log.debug("Rest request to save Company : {}", companyDTO);
        Boolean isExistingCompany = companyService.isExistingCompany(id);
        Util.validateUpdateInput(companyDTO.getId(), id, isExistingCompany);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable("id") Long id) {
        log.debug("Rest request to get Company by id : {}", id);
        CompanyDTO company = companyService.getById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/company")
    public ResponseEntity<List<CompanyDTO>> getAll() {
        log.debug("Rest request to get all Companies ");
        List<CompanyDTO> companyDTOList = companyService.getAll();
        return new ResponseEntity<>(companyDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("Rest Request to delete Company by id : {}", id);
        Boolean isExistingCompany = companyService.isExistingCompany(id);
        Util.validateDeleteInput(isExistingCompany);
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
