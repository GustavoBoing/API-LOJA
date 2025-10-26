package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="salesItem")

@Entity
public class SalesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "salesOrderId", nullable = false)
    SalesOrder salesOrderId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    Product productId;

    @Column(name="quantity", nullable = false)
    Integer quantity;

    @Column(name="total", nullable = false)
    Double priceUnit;

    public Double subtotal(){
        return quantity * priceUnit;
    }
}
