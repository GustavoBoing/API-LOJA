package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseItem;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseOrder;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseItemRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseItemService {
    private final PurchaseItemRepository purchaseItemRepository;

    public PurchaseItemService(PurchaseItemRepository purchaseItemRepository) {
        this.purchaseItemRepository = purchaseItemRepository;
    }

    public PurchaseItem findPurchaseItemById(int id) {
        return purchaseItemRepository.findPurchaseItemById(id).orElseThrow(
                () -> new RuntimeException("Id purchaseItem not found")
        );
    }

    public List<PurchaseItem> findPurchaseItemByPurchaseOrderId(int id){
        /*return purchaseItemRepository.findPurchaseItemByPurchaseOrderId_Id(id).orElseThrow(
                () -> new RuntimeException("PurchaseOrder Id is not found")
        );*/
        List<PurchaseItem> items = purchaseItemRepository.findPurchaseItemByPurchaseOrderId_Id(id);

        if(items.isEmpty()){
            throw new RuntimeException("PurchaseOrder Id is not found");
        }

        return items;
    }

    public List<PurchaseItem> findPurchaseItemByProductId(int id){
        //return purchaseItemRepository.findPurchaseItemByProductId_Id(id);
        List<PurchaseItem> items = purchaseItemRepository.findPurchaseItemByProductId_Id(id);

        if(items.isEmpty()){
            throw new RuntimeException("Product Id is not found");
        }

        return items;
    }

    public void deletePurchaseItemById(int id){ purchaseItemRepository.deletePurchaseItemById(id); }

    public void refreshPurchaseItemById(PurchaseItem purchaseItem, int id){
        PurchaseItem actualPurchaseItem = findPurchaseItemById(id);

        if(purchaseItem.getQuantity() != null){
            if(purchaseItem.getQuantity() < 0){
                actualPurchaseItem.setQuantity(purchaseItem.getQuantity());
            }
        }

        purchaseItemRepository.saveAndFlush(actualPurchaseItem);
    }
}
