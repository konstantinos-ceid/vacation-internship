package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.CompanyMapper;
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
public class CompanyService {

    private final CompanyRepository companyRepository;


    private final CompanyMapper companyMapper;


    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Transactional(readOnly = true)
    public CompanyDTO getById(Long id) {
        CompanyDTO result = new CompanyDTO();
        log.debug("Request to get Company by id : {}", id);
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            result = companyMapper.toDto(company.get());
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
        return result;
    }

    @Transactional(readOnly = true)
    public List<CompanyDTO> getAll() {
        log.debug("Request to get All Companies");
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(companyMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Company by id : {}", id);
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingCompany(Long id){
        return companyRepository.findById(id).isPresent();
    }

}
