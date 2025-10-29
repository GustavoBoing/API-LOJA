package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
    Optional<SalesOrder> findSalesOrderById(int id);
    List<SalesOrder> findSalesOrderByCustomerId_Id(int id);

    @Transactional
    void deleteSalesOrderById(int id);
}
