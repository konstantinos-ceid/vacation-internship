package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.utility.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class VacationRequestController {
    private final VacationRequestService vacationRequestService;

    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @PostMapping("/vacationRequest")
    public ResponseEntity<VacationRequestDTO> save(@RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("Rest request to save VacationRequest : {}", vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/vacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> update(@PathVariable("id") Long id, @RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("Rest request to save VacationRequest : {}", vacationRequestDTO);
        Boolean isExistingVacationRequest = vacationRequestService.isExistingVacationRequest(id);
        Util.validateUpdateInput(vacationRequestDTO.getId(), id, isExistingVacationRequest);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/vacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> getById(@PathVariable("id") Long id) {
        log.debug("Rest request to get VacationRequest by id : {}", id);
        VacationRequestDTO vacationRequest = vacationRequestService.getById(id);
        return new ResponseEntity<>(vacationRequest, HttpStatus.OK);
    }

    @GetMapping("/vacationRequest")
    public ResponseEntity<List<VacationRequestDTO>> getAll() {
        log.debug("Rest request to get all vacationRequest ");
        List<VacationRequestDTO> vacationRequestDTOList = vacationRequestService.getAll();
        return new ResponseEntity<>(vacationRequestDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/vacationRequest/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("Rest Request to delete VacationRequest by id : {}", id);
        Boolean isExistingVacationRequest = vacationRequestService.isExistingVacationRequest(id);
        Util.validateDeleteInput(isExistingVacationRequest);
        vacationRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}