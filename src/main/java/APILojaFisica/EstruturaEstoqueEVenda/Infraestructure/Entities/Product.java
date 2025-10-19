package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Product")

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NonNull
    @Column(name = "sku", nullable = false)
    String sku;

    @NonNull
    @Column(name = "name", nullable = false)
    String name;

    @Column (name = "description", nullable = false)
    String description;

    @NonNull
    @Column (name = "cost", nullable = false)
    Double cost;

    @NonNull
    @Column(name = "salePrice", nullable = false)
    Double salePrice;

    @NonNull
    @Column(name = "minimumStock")
    Integer minimumStock;
}
