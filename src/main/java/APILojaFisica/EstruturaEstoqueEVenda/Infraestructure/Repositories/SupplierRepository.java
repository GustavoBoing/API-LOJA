package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> findSupplierById(int id);
    Optional<Supplier> findSupplierByName(String name);

    @Transactional
    void deleteSupplierById(int id);
}
