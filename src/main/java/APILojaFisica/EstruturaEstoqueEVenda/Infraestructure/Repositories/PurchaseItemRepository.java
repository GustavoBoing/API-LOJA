package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseItem;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Integer> {
    Optional<PurchaseItem> findPurchaseItemById(int id);
    List<PurchaseItem> findPurchaseItemByPurchaseOrderId_Id(int id);
    List<PurchaseItem> findPurchaseItemByProductId_Id(int id);

    @Transactional
    void deletePurchaseItemById(int id);
}
