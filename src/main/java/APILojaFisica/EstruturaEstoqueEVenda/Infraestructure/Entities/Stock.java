package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="stock")

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name="idProduct", nullable = false)
    private Product product;

    @Column(name="storageUnity", nullable = false)
    String storageUnity;

    @Column(name="quantity", nullable = false)
    Integer quantity;

    @Column(name="locale", nullable = false)
    String locale;
}
