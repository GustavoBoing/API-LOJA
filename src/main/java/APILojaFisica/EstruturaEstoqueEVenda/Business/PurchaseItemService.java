package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseItem;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseOrder;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Stock;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseItemRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseItemService {
    private final PurchaseItemRepository purchaseItemRepository;
    private final StockService stockService;

    public PurchaseItemService(PurchaseItemRepository purchaseItemRepository, StockService stockService) {
        this.purchaseItemRepository = purchaseItemRepository;
        this.stockService = stockService;
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

    public void refreshPurchaseItemById(PurchaseItem purchaseItem, int id){
        PurchaseItem actualPurchaseItem = findPurchaseItemById(id);
        int productId = purchaseItem.getProductId().getId();
        Stock stock = stockService.findByStockByIdProduct(productId);

        if(purchaseItem.getQuantity() != null){
            if(purchaseItem.getQuantity() < 0){
                stock.setQuantity(stock.getQuantity() - actualPurchaseItem.getQuantity());
                stock.setQuantity(stock.getQuantity() + purchaseItem.getQuantity());
                actualPurchaseItem.setQuantity(purchaseItem.getQuantity());
            }
            else {
                throw new RuntimeException("Quantity insert is invalid");
            }
        }

        purchaseItemRepository.saveAndFlush(actualPurchaseItem);
    }
}
