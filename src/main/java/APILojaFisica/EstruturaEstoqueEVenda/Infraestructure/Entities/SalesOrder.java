package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @JsonManagedReference
    private List<SalesItem> items = new ArrayList<>();

    @Column(name="date", nullable = false)
    LocalDateTime date;

    @Column(name="status", nullable = false)
    String status;

    @Column(name="total", nullable = false)
    Double total;

    public void updateTotal(){
        this.total = items.stream()
                .mapToDouble(item -> item.subtotal() != null ? item.subtotal() : 0.0)
                .sum();
    }
}
