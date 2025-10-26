package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="purchaseItem")

@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    Product productId;

    @ManyToOne
    @JoinColumn(name = "purchaseOrderId", nullable = false)
    @JsonBackReference // Evita loop ao serializar o JSON
    PurchaseOrder purchaseOrderId;

    @Column(name="quantity", nullable = false)
    Integer quantity;

    @Column(name="priceUnit", nullable = false)
    Double  priceUnity;

    public Double subtotal(){
        return quantity * priceUnity;
    }
}
