package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="salesOrder")

@Entity
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    Customer customerId;

    @OneToMany(mappedBy = "salesOrderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesItem> items = new ArrayList<>();

    @Column(name="date", nullable = false)
    Date date;

    @Column(name="status", nullable = false)
    String status;

    @Column(name="total", nullable = false)
    Double total;

    public void updateTotal(){
        this.total = items.stream()
                .mapToDouble(SalesItem::subtotal)
                .sum();
    }
}
