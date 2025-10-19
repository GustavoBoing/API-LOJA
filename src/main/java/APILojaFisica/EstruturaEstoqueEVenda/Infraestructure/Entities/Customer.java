package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table (name="Customer")

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "numberCellPhone", nullable = false)
    String numberCellPhone;

    @Column(name = "email", nullable = false)
    String email;

}
