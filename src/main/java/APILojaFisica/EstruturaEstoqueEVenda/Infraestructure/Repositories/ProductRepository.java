package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    Optional<Product> findByName(String name);

    Optional<Product> findById(int id);

    @Transactional
    void deleteById(int id);
}
