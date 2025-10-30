package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StockRepository extends JpaRepository <Stock, Integer> {
    Optional <Stock> findByProduct(Product product);

    Optional <Stock> findByProduct_Id(int productId);

    Optional <Stock> findById(int id);

    @Transactional
    void deleteById(int id);
}
