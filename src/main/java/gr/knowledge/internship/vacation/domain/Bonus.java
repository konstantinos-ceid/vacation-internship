package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Bonus {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bonus_generator")
    @SequenceGenerator(name = "bonus_generator", sequenceName = "seq_bonus")
    @Column(name = "id" ,nullable = false)
    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @NotNull
    @Column(name = "amount" ,nullable = false)
    private Double amount;
}
