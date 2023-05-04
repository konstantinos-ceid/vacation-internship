package gr.knowledge.internship.vacation.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "vacation_request")
@AllArgsConstructor
@NoArgsConstructor
public class VacationRequest {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_request_generator")
    @SequenceGenerator(name = "vacation_request_generator", sequenceName = "seq_vacation_request")
    @Column(name = "id" ,nullable = false)
    Long id;

    @NotNull
    @Column(name = "start_date")
    LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    LocalDate endDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "status" ,nullable = false)
    String status;

    @NotNull
    @Size(max = 255)
    @Column(name = "days" ,nullable = false)
    Integer days;

}
