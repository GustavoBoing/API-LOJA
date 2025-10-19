package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Suplier")

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="phone", nullable = false)
    String phone;

    @Column(name="email", nullable = false)
    String email;
}
