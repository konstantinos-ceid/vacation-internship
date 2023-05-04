package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
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
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;

    private final VacationRequestMapper vacationRequestMapper;


    public VacationRequestService(VacationRequestRepository vacationRequestRepository, VacationRequestMapper vacationRequestMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
    }

    @Transactional(readOnly = true)
    public VacationRequestDTO getById(Long id) {
        VacationRequestDTO result = new VacationRequestDTO();
        log.debug("Request to get VacationRequest by id : {}", id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if (vacationRequest.isPresent()) {
            result = vacationRequestMapper.toDto(vacationRequest.get());
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
        return result;
    }

    @Transactional(readOnly = true)
    public List<VacationRequestDTO> getAll() {
        log.debug("Request to get All Companies");
        List<VacationRequest> vacationRequestList = vacationRequestRepository.findAll();
        return vacationRequestList.stream().map(vacationRequestMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO) {
        log.debug("Request to save VacationRequest : {}", vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest = vacationRequestRepository.save(vacationRequest);
        return vacationRequestMapper.toDto(vacationRequest);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete VacationRequest by id : {}", id);
        if (vacationRequestRepository.existsById(id)) {
            vacationRequestRepository.deleteById(id);
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingVacationRequest(Long id){
        return vacationRequestRepository.findById(id).isPresent();
    }
}