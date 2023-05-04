package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeProductMapper;
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
public class EmployeeProductService {

    private final EmployeeProductRepository employeeProductRepository;

    private final EmployeeProductMapper employeeProductMapper;


    public EmployeeProductService(EmployeeProductRepository employeeProductRepository, EmployeeProductMapper employeeProductMapper) {
        this.employeeProductRepository = employeeProductRepository;
        this.employeeProductMapper = employeeProductMapper;
    }

    @Transactional(readOnly = true)
    public EmployeeProductDTO getById(Long id) {
        EmployeeProductDTO result = new EmployeeProductDTO();
        log.debug("Request to get EmployeeProduct by id : {}", id);
        Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(id);
        if (employeeProduct.isPresent()) {
            result = employeeProductMapper.toDto(employeeProduct.get());
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
        return result;
    }

    @Transactional(readOnly = true)
    public List<EmployeeProductDTO> getAll() {
        log.debug("Request to get All Companies");
        List<EmployeeProduct> employeeProductList = employeeProductRepository.findAll();
        return employeeProductList.stream().map(employeeProductMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeProductDTO save(EmployeeProductDTO employeeProductDTO) {
        log.debug("Request to save EmployeeProduct : {}", employeeProductDTO);
        EmployeeProduct employeeProduct = employeeProductMapper.toEntity(employeeProductDTO);
        employeeProduct = employeeProductRepository.save(employeeProduct);
        return employeeProductMapper.toDto(employeeProduct);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete EmployeeProduct by id : {}", id);
        if (employeeProductRepository.existsById(id)) {
            employeeProductRepository.deleteById(id);
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingEmployeeProduct(Long id){
        return employeeProductRepository.findById(id).isPresent();
    }
}
