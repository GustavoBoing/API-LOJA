package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="purchaseOrder")

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "idSupplier", nullable = false)
    Supplier idSupplier;

    @Column(name = "date", nullable = false)
    LocalDateTime dateTime;

    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "total", nullable = false)
    Double total;

    @OneToMany(mappedBy = "purchaseOrderId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Liga com o JsonBackReference
    private List<PurchaseItem> items = new ArrayList<>();

    // metodo para atualizar o total
    public void updateTotal(){
        this.total = items.stream()
                .mapToDouble(item -> item.subtotal() != null ? item.subtotal() : 0.0)
                .sum();
    }

    public void defDate(){
        this.dateTime = LocalDateTime.now();
    }
}
