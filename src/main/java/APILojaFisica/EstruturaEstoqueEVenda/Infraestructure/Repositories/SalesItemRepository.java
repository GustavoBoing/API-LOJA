package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SalesItemRepository extends JpaRepository<SalesItem, Integer>{
    Optional<SalesItem> findSalesItemById(int id);
    List<SalesItem> findSalesItemByProductId_Id(int id);
    List<SalesItem> findSalesItemBySalesOrderId_Id(int id);
}
