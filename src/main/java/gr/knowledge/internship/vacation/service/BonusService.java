package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;
import gr.knowledge.internship.vacation.utility.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class BonusService {

    private final BonusRepository bonusRepository;


    private final BonusMapper bonusMapper;


    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
    }

    @Transactional(readOnly = true)
    public BonusDTO getById(Long id) {
        BonusDTO result = new BonusDTO();
        log.debug("Request to get Bonus by id : {}", id);
        Optional<Bonus> bonus = bonusRepository.findById(id);
        if (bonus.isPresent()) {
            result = bonusMapper.toDto(bonus.get());
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
        return result;
    }

    @Transactional(readOnly = true)
    public List<BonusDTO> getAll() {
        log.debug("Request to get All Companies");
        List<Bonus> bonusList = bonusRepository.findAll();
        return bonusList.stream().map(bonusMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public BonusDTO save(BonusDTO bonusDTO) {
        log.debug("Request to save Bonus : {}", bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        bonus = bonusRepository.save(bonus);
        return bonusMapper.toDto(bonus);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Bonus by id : {}", id);
        if (bonusRepository.existsById(id)) {
            bonusRepository.deleteById(id);
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingBonus(Long id){
        return bonusRepository.findById(id).isPresent();
    }

}