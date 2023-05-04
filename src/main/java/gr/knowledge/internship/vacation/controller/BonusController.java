package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.BonusService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.utility.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class BonusController {
    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @PostMapping("/bonus")
    public ResponseEntity<BonusDTO> save(@RequestBody BonusDTO bonusDTO) {
        log.debug("Rest request to save Bonus : {}", bonusDTO);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/bonus/{id}")
    public ResponseEntity<BonusDTO> update(@PathVariable("id") Long id, @RequestBody BonusDTO bonusDTO) {
        log.debug("Rest request to save Bonus : {}", bonusDTO);
        Boolean isExistingBonus = bonusService.isExistingBonus(id);
        Util.validateUpdateInput(bonusDTO.getId(), id, isExistingBonus);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/bonus/{id}")
    public ResponseEntity<BonusDTO> getById(@PathVariable("id") Long id) {
        log.debug("Rest request to get Bonus by id : {}", id);
        BonusDTO bonus = bonusService.getById(id);
        return new ResponseEntity<>(bonus, HttpStatus.OK);
    }

    @GetMapping("/bonus")
    public ResponseEntity<List<BonusDTO>> getAll() {
        log.debug("Rest request to get all Bonus ");
        List<BonusDTO> bonusDTOList = bonusService.getAll();
        return new ResponseEntity<>(bonusDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/bonus/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("Rest Request to delete Bonus by id : {}", id);
        Boolean isExistingBonus = bonusService.isExistingBonus(id);
        Util.validateDeleteInput(isExistingBonus);
        bonusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}