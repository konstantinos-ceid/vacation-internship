package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestDTO implements Serializable {

    Long id;

    LocalDate startDate;

    LocalDate endDate;

    @Size(max = 255)
    String status;

    @Size(max = 255)
    Integer days;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employeeDTO;
}
