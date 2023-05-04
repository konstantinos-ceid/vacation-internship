package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getById(Long id) {
        EmployeeDTO result = new EmployeeDTO();
        log.debug("Request to get Employee by id : {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            result = employeeMapper.toDto(employee.get());
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
        return result;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAll() {
        log.debug("Request to get All Companies");
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(employeeMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Employee by id : {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingEmployee(Long id){
        return employeeRepository.findById(id).isPresent();
    }
}
